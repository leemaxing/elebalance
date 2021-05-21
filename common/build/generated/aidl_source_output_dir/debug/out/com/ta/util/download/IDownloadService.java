/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.ta.util.download;
public interface IDownloadService extends android.os.IInterface
{
  /** Default implementation for IDownloadService. */
  public static class Default implements com.ta.util.download.IDownloadService
  {
    @Override public void startManage() throws android.os.RemoteException
    {
    }
    @Override public void addTask(java.lang.String url) throws android.os.RemoteException
    {
    }
    @Override public void pauseTask(java.lang.String url) throws android.os.RemoteException
    {
    }
    @Override public void pauseAll(java.lang.String url) throws android.os.RemoteException
    {
    }
    @Override public void deleteTask(java.lang.String url) throws android.os.RemoteException
    {
    }
    @Override public void continueTask(java.lang.String url) throws android.os.RemoteException
    {
    }
    @Override public void stopManage() throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.ta.util.download.IDownloadService
  {
    private static final java.lang.String DESCRIPTOR = "com.ta.util.download.IDownloadService";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.ta.util.download.IDownloadService interface,
     * generating a proxy if needed.
     */
    public static com.ta.util.download.IDownloadService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.ta.util.download.IDownloadService))) {
        return ((com.ta.util.download.IDownloadService)iin);
      }
      return new com.ta.util.download.IDownloadService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_startManage:
        {
          data.enforceInterface(descriptor);
          this.startManage();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_addTask:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.addTask(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_pauseTask:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.pauseTask(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_pauseAll:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.pauseAll(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_deleteTask:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.deleteTask(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_continueTask:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.continueTask(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_stopManage:
        {
          data.enforceInterface(descriptor);
          this.stopManage();
          reply.writeNoException();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.ta.util.download.IDownloadService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public void startManage() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_startManage, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().startManage();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void addTask(java.lang.String url) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(url);
          boolean _status = mRemote.transact(Stub.TRANSACTION_addTask, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().addTask(url);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void pauseTask(java.lang.String url) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(url);
          boolean _status = mRemote.transact(Stub.TRANSACTION_pauseTask, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().pauseTask(url);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void pauseAll(java.lang.String url) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(url);
          boolean _status = mRemote.transact(Stub.TRANSACTION_pauseAll, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().pauseAll(url);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void deleteTask(java.lang.String url) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(url);
          boolean _status = mRemote.transact(Stub.TRANSACTION_deleteTask, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().deleteTask(url);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void continueTask(java.lang.String url) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(url);
          boolean _status = mRemote.transact(Stub.TRANSACTION_continueTask, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().continueTask(url);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void stopManage() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_stopManage, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().stopManage();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      public static com.ta.util.download.IDownloadService sDefaultImpl;
    }
    static final int TRANSACTION_startManage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_pauseTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_pauseAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_deleteTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_continueTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_stopManage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    public static boolean setDefaultImpl(com.ta.util.download.IDownloadService impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.ta.util.download.IDownloadService getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public void startManage() throws android.os.RemoteException;
  public void addTask(java.lang.String url) throws android.os.RemoteException;
  public void pauseTask(java.lang.String url) throws android.os.RemoteException;
  public void pauseAll(java.lang.String url) throws android.os.RemoteException;
  public void deleteTask(java.lang.String url) throws android.os.RemoteException;
  public void continueTask(java.lang.String url) throws android.os.RemoteException;
  public void stopManage() throws android.os.RemoteException;
}
