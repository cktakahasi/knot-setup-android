package br.org.cesar.knot_setup_app.view.configureDevice;

public interface ConfigureDeviceContract {

    interface ViewModel {
        void onConnected();
        void onDisconnected();
        void onWriteSucceeded(int write_count);
        void onWriteFailed(int write_count);
        void onErrorHandler(Throwable throwable);
    }

    interface Presenter {
        void onFocus();
    }
}
