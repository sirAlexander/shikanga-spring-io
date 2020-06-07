package com.shikanga.awsimageupload.datastore;

import com.shikanga.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("4f142615-2edb-40b4-8718-db560667af09"), "janetjones", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("052f5136-4ca3-4286-a087-eca5c4ff6c4c"), "antoniojunior", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
