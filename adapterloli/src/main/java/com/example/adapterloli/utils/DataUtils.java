package com.example.adapterloli.utils;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataUtils {

    public static boolean isEmptyArray(Object[] array) {
        return array == null || 0 == array.length;
    }

    public static boolean isEmptyList(Collection list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmptyMap(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmptyData(Object data) {
        if (data == null) {
            return true;
        }
        if (data instanceof Collection) {
            return ((Collection) data).isEmpty();
        }
        if (data instanceof Map) {
            return ((Map) data).isEmpty();
        }
        if (data instanceof String) {
            return ((String) data).isEmpty();
        }
        return false;
    }

    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        } else {
            return obj;
        }
    }

    public static <D> D getItem(D[] array, int index) {
        if (isEmptyArray(array)) {
            return null;
        }
        if (index < 0 || array.length <= index) {
            return null;
        }
        return array[index];
    }

    public static <D> D getItem(List<D> list, int index) {
        if (null == list || index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    public static <D> D getItem(List<D> list, int index, D defaultValue) {
        D item = getItem(list, index);
        return item == null ? defaultValue : item;
    }

    public static <D> D getValidItem(@NonNull List<D> list, int index) {
        if (0 > index) {
            return list.get(0);
        }
        int maxIndex = list.size() - 1;
        if (index > maxIndex) {
            return list.get(maxIndex);
        }
        return list.get(index);
    }

    public static <D> boolean trySetListItem(List<D> list, int index, D d) {
        if (isEmptyList(list)) {
            return false;
        }
        if (0 > index) {
            return false;
        }
        if (index > list.size() - 1) {
            return false;
        }
        list.set(index, d);
        return true;
    }

    public static int getCollectionSize(Collection collection) {
        if (null == collection) {
            return 0;
        }
        return collection.size();
    }

    public static void clearCollection(Collection list) {
        if (null == list) {
            return;
        }
        list.clear();
    }

    public static ArrayList getMapValues(Map map) {
        ArrayList list = new ArrayList();
        if (map == null || map.isEmpty()) {
            return list;
        }
        list.addAll(map.values());
        return list;
    }

    public static int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static <D> int indexOf(D[] array, D value) {
        for (int i = 0; i < array.length; i++) {
            if (equals(array[i], value)) {
                return i;
            }
        }
        return -1;
    }

    public static <D> boolean equals(D data1, D data2) {
        if (data1 == null) {
            return data2 == null;
        }
        return data1.equals(data2);
    }

    public static <D> void putAll(SparseArray<D> src, SparseArray<? extends D> params) {
        if (src == null || params == null || params.size() == 0) {
            return;
        }
        for (int i = 0; i < params.size(); i++) {
            src.put(params.keyAt(i), params.valueAt(i));
        }
    }

    public static <T> List<List<T>> splitList(List<T> resList, List<List<T>> originalList, int count) {
        if (resList == null || count < 1)
            return null;

        int size = resList.size();
        if (size <= count) { //数据量不足count指定的大小
            originalList.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                originalList.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                originalList.add(itemList);
            }
        }
        return originalList;
    }

    public static boolean setAll(Collection srcList, Collection paramList) {
        if (srcList == null) {
            return false;
        }
        boolean isParamEmpty = isEmptyList(paramList);
        if (srcList.isEmpty() && isParamEmpty) {
            return false;
        }
        srcList.clear();
        if (!isParamEmpty) {
            srcList.addAll(paramList);
        }
        return true;
    }

}
