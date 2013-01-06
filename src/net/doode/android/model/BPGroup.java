package net.doode.android.model;

import java.net.URI;


public class BPGroup {

    private String mName;
    private String mDescription;
    private URI    mAvatarUri;

    public BPGroup(String name) {
        this(name, "");
    }

    public BPGroup(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public URI getAvatarUri() {
        return mAvatarUri;
    }

    public void setAvatarUri(URI avatarUri) {
        mAvatarUri = avatarUri;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

}
