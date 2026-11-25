package org.example.springboot.controller;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.entity.*;
import org.example.springboot.exception.CustomException;
import org.example.springboot.mapper.*;
import org.example.springboot.service.EmployeeService;
import org.example.springboot.service.ItemService;
import org.example.springboot.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.springboot.service.EmployeeServicempl;
import org.example.springboot.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.example.springboot.common.Result;
import org.example.springboot.common.FileVO;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
public class TestController {
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private FileService fileService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClaimMapper claimMapper;
    @Autowired
    private ItemMapper itemMapper;
   @GetMapping("/emp/list")
   public List<Employee> getEmpList(){
       List<Employee> empList=employeeMapper.selectAll();
       return  empList;

   }
   @GetMapping("setbyId/{id}")
   public Employee setbyId(@PathVariable Integer id){
       Employee employee=employeeMapper.selectbyId(id);
       return  employee;
   }
   @GetMapping("/empt")
   public Employee empt(@RequestParam Integer id){
       return employeeMapper.selectbyId(id);
   }
    @GetMapping("/hello")
    public Result hello(){
        return Result.success("9090端口访问成功");
    }
    @GetMapping("/count")
    public Result count(){
        throw new CustomException("400","请求错误（失败）");

    }
    @GetMapping("/map")
    public Result map(){
        int a=1/0;
        HashMap<String,Object> map=new HashMap<>();
        map.put("name","heyongyun");
        map.put("age",10);
        return Result.success(map);
    }
    @GetMapping("/result")
    public Result result(){
        return Result.success();
    }
    @GetMapping("/results")
    public Result results(){
        return Result.success(10);
    }
    @PutMapping("/putdata/{id}")
    public Result updataBy(@PathVariable Integer id,@RequestBody Employee employee){
       employee.setId(id);
       employeeMapper.updataByID(employee);
       return  Result.success("修改成功");

    }
    @PostMapping("/Add")
        public Result AddData (@RequestBody Employee employee){
       System.out.println(employee);
       int rows=employeeMapper.addData(employee);
       return  Result.success("数据新增成功");
    }

    @DeleteMapping("/delectdata/{id}")
    public Result deletdata(@PathVariable Integer id){

       int m=employeeMapper.delectdata(id);
       return  Result.success("删除成功");
    }
    @GetMapping("/emp/page")
    public PageInfo<Employee> empPage(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "5" ) Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> list=employeeMapper.selectAll();
        return  new PageInfo<>(list);

    }
    @GetMapping("page")
    public PageInfo<Employee> page(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
       return employeeService.getEmpPage(pageNum,pageSize);

    }
    @GetMapping("/seacher")
        public PageInfo<Employee> seacher(@RequestParam String name,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
       PageHelper.startPage(pageNum,pageSize);
       List<Employee> employeeList=employeeMapper.search(name);
       return  new PageInfo<>(employeeList);



        }
        @GetMapping("/login")
    public Result login(@RequestParam String name,@RequestParam Integer id){
       Employee employee=employeeMapper.login(name,id);
       if(employee==null){
           return Result.error("账号或id错误");
       }
       String token=jwtUtil.Createtoken((long)employee.getId());

       return Result.success(token) ;

        }




        @PostMapping("file/upload")
    public Result fileUpload(MultipartFile file)throws IOException {
       String originalFilename=file.getOriginalFilename();
       String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
       String saveFilename= UUID.randomUUID()+suffix;
       File folder=new File("D:/print_upload/");
       if(!folder.exists()){
           folder.mkdirs();
       }
       File targetFile=new File(folder,saveFilename);
       file.transferTo(targetFile);
       FileVO vo=new FileVO();
       vo.setFileName(saveFilename);
       return  Result.success(vo);



        }

        @GetMapping("file/preview")
    public void preview(@RequestParam String fileName, HttpServletResponse response)throws IOException{
       fileService.previewFile(fileName,response);

    }



    @PostMapping("post")
    public  Result posts(@RequestBody Student student){
       Integer students=studentMapper.post(student);
       return Result.success(students);
    }


        @Autowired
        private ItemService itemService;

        // 发布物品接口
        @PostMapping("/publish")
        public Map<String, Object> publishItem(
                @RequestParam("userId") Integer userId,
                @RequestParam("intent") String intent,
                @RequestParam("itemName") String itemName,
                @RequestParam("category") String category,
                @RequestParam("location") String location,
                @RequestParam("occurDate") String occurDate,
                @RequestParam(value = "uniqueMark", required = false) String uniqueMark,
                @RequestParam(value = "description", required = false) String description,
                @RequestParam("posterWhatsapp") String posterWhatsapp,
                @RequestParam(value = "image", required = false) MultipartFile image) {
            // 👆 注意：这里已经删掉了最后那个要命的 @RequestHeader(value = "token")

            // 临时简化鉴权，登录模块做好后替换


            String imageUrl = null;
            if(image != null && !image.isEmpty()){
                imageUrl = itemService.saveImage(image);
                System.out.println("【保存图片返回url】：" + imageUrl);
            }

            Item item = new Item();
            item.setItemName(itemName);
            item.setCategoryId(Integer.parseInt(category));
            item.setLocation(location);
            item.setIntent(intent);
            item.setOccurDate(occurDate);
            item.setUniqueMark(uniqueMark);
            item.setDescription(description);
            item.setImageUrl(imageUrl);
            System.out.println("【存入item实体的imageUrl】：" + item.getImageUrl());
            item.setUserId(userId);
            item.setPosterWhatsapp(posterWhatsapp);
            item.setPublishTime(LocalDate.now().toString());

            int row = itemService.addItem(item);

            Map<String, Object> res = new HashMap<>();
            res.put("code", row > 0 ? 200 : 500);
            res.put("msg", row > 0 ? "success" : "fail");
            return res;
        }

        // 物品列表+筛选接口
        @PostMapping("/list")
        public Map<String, Object> getItemList(@RequestBody Map<String, String> params) {
            String category = params.get("category");
            String location = params.get("location");
            String sortDate = params.get("sortDate");
            String itemName=params.get("itemName");
            String intent=params.get("intent");

            List<Item> list = itemService.getItemList(category, location, sortDate,itemName,intent);

            Map<String, Object> res = new HashMap<>();
            res.put("code", 200);
            res.put("data", list);
            return res;
        }
    @GetMapping("/admin/pending-items")
    public Result getPendingItems() {
        // 调用你的 Service 层查询 status = 'pending' 的物品
        List<Item> list = itemService.listByStatus("pending");
        return Result.success(list);
    }

    // 2. 审核物品（批准 approved 或拒绝 rejected）
    @PostMapping("/admin/audit-item")
    public Result auditItem(@RequestParam("itemId") Integer itemId, @RequestParam("status") String status) {
        // 调用你的 Service 层更新物品状态
        boolean success = itemService.updateItemStatus(itemId, status);
        if (success) {
            return Result.success("Audit status updated successfully");
        } else {
            return Result.error("Operation failed");
        }
    }
    // 1. 获取所有认领申请列表（供管理员查看）
    @GetMapping("/api/claims/all")
    public Map<String, Object> getAllClaims() {
        Map<String, Object> response = new HashMap<>();
        try {
            // 需要在 ClaimMapper 中写一个 selectAllClaims 方法
            List<Claim> list = claimMapper.selectAllClaims();
            response.put("code", 200);
            response.put("data", list);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "Error: " + e.getMessage());
        }
        return response;
    }

    // 2. 更新认领状态（管理员点击同意/拒绝时调用）
    @PostMapping("/api/claim/updateStatus")
    public Map<String, Object> updateClaimStatus(@RequestBody Claim claim) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 前端传过来 claimId 和新的 status (比如 "approved" 或 "rejected")
            int rows = claimMapper.updateStatus(claim.getClaimId(), claim.getStatus());
            if (rows > 0) {
                response.put("code", 200);
                response.put("msg", "Status updated successfully");
            } else {
                response.put("code", 500);
                response.put("msg", "Update failed");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "Error: " + e.getMessage());
        }
        return response;
    }
    @GetMapping("/api/claim/history")
    public Result getClaimHistory() {
        List<Claim> historyList = claimMapper.selectClaimHistory();
        return Result.success(historyList);
    }
    /**
     * 1. 获取当前用户发布的物品记录
     */
    @GetMapping("/api/user/my-items")
    public Result getMyPublishedItems(@RequestParam("userId") Long userId) {
        try {
            List<Item> itemList = itemMapper.selectItemsByUserId(userId);
            return Result.success(itemList);
        } catch (Exception e) {
            return Result.error("Error: " + e.getMessage());
        }
    }

    /**
     * 2. 获取当前用户提交的认领记录（联查对应的物品信息、照片、地点等）
     */
    @GetMapping("/api/user/my-claims")
    public Result getMyClaimHistory(@RequestParam("userId") Long userId) {
        try {
            List<Claim> claimList = claimMapper.selectClaimsWithItemByUserId(userId);
            return Result.success(claimList);
        } catch (Exception e) {
            return Result.error("Error: " + e.getMessage());
        }
    }
    @PostMapping("/api/user/delete-item")
    public Result deleteMyItem(@RequestParam("itemId") Long itemId) {
        int rows = itemMapper.deleteById(itemId);
        return rows > 0 ? Result.success(null) : Result.error("Failed to delete item");
    }

    @PostMapping("/api/user/delete-claim")
    public Result deleteMyClaim(@RequestParam("claimId") Long claimId) {
        int rows = claimMapper.deleteById(claimId);
        return rows > 0 ? Result.success(null) : Result.error("Failed to delete claim");
    }
    @GetMapping("/test")
    public String test(){
        return "Backend connection successfull";
    }
    }






