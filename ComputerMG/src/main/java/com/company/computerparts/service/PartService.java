package com.company.computerparts.service;

import com.company.computerparts.dao.PartDao;
import com.company.computerparts.model.Part;
import com.company.computerparts.model.PartQuery;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PartService {
    private final PartDao partDao;

    public PartService(PartDao partDao) {
        this.partDao = partDao;
    }

    public boolean addPart(Part part) {
        try {
            // 业务逻辑验证
            if (part.getName() == null || part.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("配件名称不能为空");
            }

            return partDao.addPart(part);
        } catch (Exception e) {
            // 记录日志
            System.err.println("添加配件失败: " + e.getMessage());
            return false;
        }
    }

    public List<Part> searchParts(PartQuery query) {
        if (!query.isValid()) {
            throw new IllegalArgumentException("查询条件不能为空");
        }

        try {
            return partDao.searchParts(query);
        } catch (SQLException e) {
            throw new RuntimeException("查询配件时发生数据库错误", e);
        }
    }

    public List<Part> getAllParts() {
        try {
            List<Part> parts = partDao.findAllParts();
            System.out.println("从DAO获取到" + parts.size() + "条记录"); // 调试输出
            return parts;
        } catch (SQLException e) {
            System.err.println("查询数据库出错: " + e.getMessage());
            return Collections.emptyList(); // 返回空列表而不是null
        }
    }

    public List<Part> advancedSearch(String id, String name, String spec, String type, double minPrice, double maxPrice) {
        BigDecimal minPriceBD = BigDecimal.valueOf(minPrice);
        BigDecimal maxPriceBD = BigDecimal.valueOf(maxPrice);

        try {
            return partDao.advancedSearch(id, name, spec, type, minPriceBD, maxPriceBD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletePart(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("配件ID无效");
        }

        try {
            // 1. 检查配件是否存在
            Part existingPart = partDao.findById(id);
            if (existingPart == null) {
                System.err.println("删除失败: 未找到ID为 " + id + " 的配件");
                return false;
            }

            // 2. 检查库存是否为零（可选业务规则）
            if (existingPart.getStockQuantity() != null && existingPart.getStockQuantity() > 0) {
                System.err.println("删除失败: 配件 " + existingPart.getName() + " 仍有库存");
                return false;
            }

            // 3. 执行删除操作
            boolean deleted = partDao.deletePart(id);

            if (deleted) {
                System.out.println("成功删除配件: " + existingPart.getName());
            } else {
                System.err.println("删除配件失败，ID: " + id);
            }

            return deleted;

        } catch (SQLException e) {
            System.err.println("删除配件时数据库错误: " + e.getMessage());
            throw new RuntimeException("删除配件时发生数据库错误", e);
        } catch (Exception e) {
            System.err.println("删除配件时发生意外错误: " + e.getMessage());
            throw new RuntimeException("删除配件失败", e);
        }
    }

    public boolean updatePart(Part updatedPart) {
        return true;
    }

    // 其他业务方法...
}