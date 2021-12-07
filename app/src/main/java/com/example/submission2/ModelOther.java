package com.example.submission2;

public class ModelOther {

    private String name;
    private String type;
    private String avatarUrl;

    public ModelOther(String name, String type, String avatarUrl) {
        this.name = name;
        this.type = type;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


}
