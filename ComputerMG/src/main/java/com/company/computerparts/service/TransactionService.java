package com.company.computerparts.service;

import com.company.computerparts.dao.TransactionDao;
import com.company.computerparts.model.InventoryRecord;
import com.company.computerparts.model.TransactionModel;

import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao;


    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public List<InventoryRecord> getTransactionsType(String type) {
        return transactionDao.findType(type);
    }
}
