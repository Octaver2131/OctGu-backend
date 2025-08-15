package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.DeleteRequest;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.model.dto.item.ItemAddRequest;
import com.yupi.project.model.dto.item.ItemQueryRequest;
import com.yupi.project.model.dto.item.ItemUpdateRequest;
import com.yupi.project.model.entity.Item;
import com.yupi.project.model.vo.ItemVO;
import com.yupi.project.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物品控制器
 *
 * @author yupi
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Resource
    private ItemService itemService;

    // region 增删改查

    /**
     * 创建物品
     *
     * @param itemAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Integer> addItem(@RequestBody ItemAddRequest itemAddRequest, HttpServletRequest request) {
        if (itemAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemAddRequest, item);
        // 校验
        itemService.validItem(item, true);
        boolean result = itemService.save(item);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(item.getId());
    }

    /**
     * 删除物品
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteItem(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = itemService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新物品
     *
     * @param itemUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateItem(@RequestBody ItemUpdateRequest itemUpdateRequest, HttpServletRequest request) {
        if (itemUpdateRequest == null || itemUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemUpdateRequest, item);
        // 校验
        itemService.validItem(item, false);
        boolean result = itemService.updateById(item);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取物品
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<ItemVO> getItemById(int id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Item item = itemService.getById(id);
        if (item == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(item, itemVO);
        return ResultUtils.success(itemVO);
    }

    /**
     * 获取物品列表
     *
     * @param itemQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<ItemVO>> listItems(ItemQueryRequest itemQueryRequest, HttpServletRequest request) {
        Item itemQuery = new Item();
        if (itemQueryRequest != null) {
            BeanUtils.copyProperties(itemQueryRequest, itemQuery);
        }
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>(itemQuery);
        List<Item> itemList = itemService.list(queryWrapper);
        List<ItemVO> itemVOList = itemList.stream().map(item -> {
            ItemVO itemVO = new ItemVO();
            BeanUtils.copyProperties(item, itemVO);
            return itemVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(itemVOList);
    }

    /**
     * 分页获取物品列表
     *
     * @param itemQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ItemVO>> listItemByPage(ItemQueryRequest itemQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        Item itemQuery = new Item();
        if (itemQueryRequest != null) {
            BeanUtils.copyProperties(itemQueryRequest, itemQuery);
            current = itemQueryRequest.getCurrent();
            size = itemQueryRequest.getPageSize();
        }
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>(itemQuery);
        Page<Item> itemPage = itemService.page(new Page<>(current, size), queryWrapper);
        Page<ItemVO> itemVOPage = new PageDTO<>(itemPage.getCurrent(), itemPage.getSize(), itemPage.getTotal());
        List<ItemVO> itemVOList = itemPage.getRecords().stream().map(item -> {
            ItemVO itemVO = new ItemVO();
            BeanUtils.copyProperties(item, itemVO);
            return itemVO;
        }).collect(Collectors.toList());
        itemVOPage.setRecords(itemVOList);
        return ResultUtils.success(itemVOPage);
    }

    // endregion
}