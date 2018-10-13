package com.wcf.service;

import com.wcf.entity.Manager;

public interface ManagerService {
    Manager queryOne(String name);
    void modifyPassword(Manager manager);
}
