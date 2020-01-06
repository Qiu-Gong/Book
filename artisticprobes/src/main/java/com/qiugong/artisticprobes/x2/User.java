package com.qiugong.artisticprobes.x2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author qzx 20/1/6.
 */
public class User implements Serializable {

    public int userId;
    public String userNmae;
    public boolean isMale;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userNmae='" + userNmae + '\'' +
                ", isMale=" + isMale +
                '}';
    }

    public User(int userId, String userNmae, boolean isMale) {
        this.userId = userId;
        this.userNmae = userNmae;
        this.isMale = isMale;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User(111, "QiuGong", false);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("cache.txt"));
        out.writeObject(user);

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("cache.txt"));
        User u = (User) in.readObject();
        in.close();
        System.out.println("user:" + u.toString());
    }
}
