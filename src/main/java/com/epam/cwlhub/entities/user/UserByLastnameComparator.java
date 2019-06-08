package com.epam.cwlhub.entities.user;

import java.util.Comparator;

public class UserByLastnameComparator implements Comparator<UserEntity> {
    @Override
    public int compare(UserEntity o1, UserEntity o2) {
        return o1.getLastName().compareTo(o2.getLastName());
    }
}
