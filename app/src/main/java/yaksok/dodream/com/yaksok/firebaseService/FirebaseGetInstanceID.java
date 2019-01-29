package yaksok.dodream.com.yaksok.firebaseService;

        import com.google.firebase.iid.FirebaseInstanceId;
        import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseGetInstanceID extends FirebaseInstanceIdService {

    public FirebaseGetInstanceID() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }


}

