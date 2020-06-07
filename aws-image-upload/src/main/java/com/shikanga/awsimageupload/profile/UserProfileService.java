package com.shikanga.awsimageupload.profile;

import com.shikanga.awsimageupload.bucket.BucketName;
import com.shikanga.awsimageupload.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                              FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        // 1. check if image is not empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
        // 2. check if file is an image
        if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image");
        }
        // 3. check if user exists in our database
        UserProfile userProfile = userProfileDataAccessService.getUserProfiles()
                .stream()
                .filter(uProfile -> uProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));

        // 4. grab some metadata from the file if any
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        // 5. store the image in s3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userProfile.getUserProfileId());
        String fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
