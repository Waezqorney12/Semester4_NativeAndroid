package com.example.myapplication;

public interface OwnerCallBack {
        void onOwnerReceived(String phone);
        void onOwnerError(Throwable t);

}
