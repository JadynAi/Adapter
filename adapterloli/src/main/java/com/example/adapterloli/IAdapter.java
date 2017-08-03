package com.example.adapterloli;

import java.util.List;

interface IAdapter {

    int getItemCount();

    void notifyDataSetChanged();

    List<?> getDataList();
}
