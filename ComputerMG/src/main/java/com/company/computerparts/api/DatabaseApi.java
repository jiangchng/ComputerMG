package com.company.computerparts.api;

import com.company.computerparts.config.DatabaseConfig;
import com.company.computerparts.dao.InventoryDao;
import com.company.computerparts.dao.PartDao;
import com.company.computerparts.dao.TransactionDao;
import com.company.computerparts.dao.UserDao;
import com.company.computerparts.service.InventoryService;
import com.company.computerparts.service.PartService;
import com.company.computerparts.service.TransactionService;
import com.company.computerparts.service.UserService;

public interface DatabaseApi {

    //初始化配置
    DatabaseConfig DATABASE_CONFIG = new DatabaseConfig();

    //初始化DAO
    UserDao USER_DAO = new UserDao(DATABASE_CONFIG);
    InventoryDao INVENTORY_DAO = new InventoryDao(DATABASE_CONFIG);
    PartDao PART_DAO = new PartDao(DATABASE_CONFIG);
    TransactionDao TRANSACTION_DAO = new TransactionDao(DATABASE_CONFIG);

    //初始化Serivce
    UserService USER_SERVICE = new UserService(USER_DAO);
    PartService PART_SERVICE = new PartService(PART_DAO);
    InventoryService INVENTORY_SERVICE = new InventoryService(INVENTORY_DAO);
    TransactionService TRANSACTION_SERVICE = new TransactionService(TRANSACTION_DAO);

}