package com.zy.graduation1.controller;

import com.google.common.collect.Lists;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.common.RequestUser;
import com.zy.graduation1.dto.user.UserDto;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.service.*;
import javafx.scene.control.Cell;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    private UserInfoManageService userInfoManageService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private ClassService classService;

    @RequestMapping("/queryUser")
    public MyPage<UserDto> queryUser(Long majorId, Long classId,
                                     String nameOrId, @RequestParam(defaultValue = "1") Integer page) {
        return userInfoManageService.queryUser(RequestUser.getCurrentUser(), majorId, classId, nameOrId, page);
    }

    @RequestMapping("/getUser")
    public UserDto getUser(@NotNull(message = "请选择需要修改的学生信息") Long studentId) {
        return userInfoManageService.getUser(studentId);
    }

    @RequestMapping("/deletedUser")
    public void deletedUser(@NotNull(message = "请选择需要删除的用户") Long studentId) {
        userService.deleteUser(studentId);
    }

    @RequestMapping("/listMenu")
    public List<Menu> listMenu() {
        return userInfoManageService.listMenu(RequestUser.getCurrentUser());
    }

    @RequestMapping("/saveOrUpdateUser")
    public void saveOrUpdateUser(@NotNull(message = "请输入学号") Long studentId, @NotBlank(message = "请输入学生姓名") String userName,
                                 @NotBlank(message = "请输入所属学院") String collegeName, @NotBlank(message = "请输入所属的专业") String majorName,
                                 @NotBlank(message = "请输入所属班级") String className, @NotBlank(message = "请输入手机号") String mobile,
                                 @NotNull(message = "请选择性别") Integer gender, @NotBlank(message = "请输入出生日期") String birthday,
                                 @NotBlank(message = "请输入邮箱") String email, @RequestParam(defaultValue = "0") Integer status,
                                 @NotBlank(message = "请输入当前所在城市") String currentCity, @NotBlank(message = "请输入家庭地址") String homeAddress,
                                 String qq, String wechat, String introduce) {
        Long collegeId = collegeService.validCollege(collegeName);
        Long majorId = majorService.validMajor(majorName, collegeId);
        Long classId = classService.validClass(collegeId, majorId, className);

        userService.saveOrUpdateUser(studentId, userName, collegeId, majorId, classId, mobile, gender, birthday,
                email, status, currentCity, homeAddress, qq, wechat, introduce);
    }

    @RequestMapping("/getUserInfo")
    public User getUserInfo() {
        return userService.selectById(RequestUser.getCurrentUser());
    }

    @RequestMapping("/excelImport")
    public void excelImport(MultipartFile file, HttpServletRequest request) throws IOException {
        List<User> users = Lists.newArrayList();
        XSSFWorkbook workbook = null;
        CommonsMultipartFile cmf = (CommonsMultipartFile)file;
        DiskFileItem dfi = (DiskFileItem)cmf.getFileItem();
        File fo = dfi.getStoreLocation();
        //创建Excel，读取文件内容
        workbook = new XSSFWorkbook(FileUtils.openInputStream(fo));
        //获取第一个工作表
        XSSFSheet sheet = workbook.getSheet("学员信息");

        //获取sheet中第一行行号
        int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();

        try {
            //循环插入数据
            for(int i=firstRowNum+1;i<=lastRowNum;i++){
                XSSFRow row = sheet.getRow(i);

                User user = new User();

                //学员名称
                XSSFCell studentId = row.getCell(0);
                if(studentId != null){
                    studentId.setCellType(CellType.STRING);
                    user.setUserName((studentId.getStringCellValue()));
                }

                //学员名称
                XSSFCell username = row.getCell(0);
                if(username!=null){
                    username.setCellType(CellType.STRING);
                    user.setUserName((username.getStringCellValue()));
                }
                users.add(user);
            }
            //usersMapper.insert(list);//往数据库插入数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }
}
