package com.company.computerparts.service;

import com.company.computerparts.dao.InventoryDao;

import java.time.LocalDate;

public class InventoryService {

    private final InventoryDao inventoryDao;

    public InventoryService(InventoryDao inventoryDao){
        this.inventoryDao = inventoryDao;
    }

    public boolean stockOut(int partId, String partName, LocalDate stockdata, int quantity, String remarks) {
        return inventoryDao.stockOut(partId, partName,  stockdata, quantity, remarks);
    }

    public boolean stockIn(int partId, String partName, LocalDate stockdata, int quantity, String remarks) {
        return inventoryDao.stockIn(partId, partName,  stockdata, quantity, remarks);
    }

    public String getPartNameById(int partId) {
        return inventoryDao.getPartNameById(partId);
    }
}
