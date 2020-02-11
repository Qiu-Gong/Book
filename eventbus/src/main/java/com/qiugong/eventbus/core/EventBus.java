package com.qiugong.eventbus.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qzx 20/2/11.
 */
public class EventBus {

    private static final String TAG = "EventBus";

    private static EventBus instance = new EventBus();
    final private Map<Object, List<SubscribeMethod>> cacheMap;
    final private Handler handler;
    final private ExecutorService executors;

    public static EventBus getInstance() {
        return instance;
    }

    private EventBus() {
        this.cacheMap = new HashMap<>();
        this.handler = new Handler(Looper.getMainLooper());
        this.executors = Executors.newCachedThreadPool();
    }

    public void register(Object subscribe) {
        Log.d(TAG, "register object:" + subscribe.getClass().getSimpleName());
        List<SubscribeMethod> list = cacheMap.get(subscribe);

        if (list == null) {
            list = getSubscribeMethods(subscribe);
            cacheMap.put(subscribe, list);
        }
    }

    private List<SubscribeMethod> getSubscribeMethods(Object subscribe) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> cls = subscribe.getClass();

        while (cls != null) {
            String name = cls.getName();
            if (name.startsWith("java.") ||
                    name.startsWith("javax.") ||
                    name.startsWith("android.") ||
                    name.startsWith("androidx.")) {
                break;
            }

            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Subscribe annotation = method.getAnnotation(Subscribe.class);
                if (annotation == null) {
                    continue;
                }

                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException("只能接收一个参数");
                }

                Log.d(TAG, "Subscribe Method: " + method.getName()
                        + " parameter:" + Arrays.toString(parameterTypes));
                ThreadMode threadMode = annotation.threadMode();
                SubscribeMethod subscribeMethod = new SubscribeMethod(method, parameterTypes[0], threadMode);
                list.add(subscribeMethod);
            }
            cls = cls.getSuperclass();
        }
        return list;
    }

    public void unregister(Object subscribe) {
        Log.d(TAG, "unregister object:" + subscribe.getClass().getSimpleName());
        List<SubscribeMethod> list = cacheMap.get(subscribe);
        if (list != null) {
            cacheMap.remove(subscribe);
        }
    }

    public void post(final Object obj) {
        Set<Object> set = cacheMap.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            final Object cls = iterator.next();

            List<SubscribeMethod> methods = cacheMap.get(cls);
            for (final SubscribeMethod method : methods) {
                if (method.getType().isAssignableFrom(obj.getClass())) {
                    Log.d(TAG, "post"
                            + "-> thread:" + method.getThreadMode()
                            + " | cls -> " + cls.getClass().getSimpleName()
                            + " | method:" + method.getMethod().getName()
                            + " | params:" + method.getType().getSimpleName());
                    switch (method.getThreadMode()) {
                        case MAIN:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                invoke(method, cls, obj);
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(method, cls, obj);
                                    }
                                });
                            }
                            break;

                        case ASYNC:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                executors.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(method, cls, obj);
                                    }
                                });
                            } else {
                                invoke(method, cls, obj);
                            }
                            break;

                        case POSTING:
                            invoke(method, cls, obj);
                            break;
                    }
                }
            }
        }
    }

    private void invoke(SubscribeMethod subscribeMethod, Object cls, Object obj) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(cls, obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
