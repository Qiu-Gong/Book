package com.qiugong.first.x09_iterator.menu;

import com.qiugong.first.x09_iterator.MenuItem;

import java.util.Iterator;

public interface Menu {
    public Iterator<MenuItem> createIterator();
}
