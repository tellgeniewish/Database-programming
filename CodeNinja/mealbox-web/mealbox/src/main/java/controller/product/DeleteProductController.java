package controller.product;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controller.Controller;
import model.service.ProductManager;

public class DeleteProductController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception	{
		ProductManager manager = ProductManager.getInstance();        

        Map<String, String[]> paramMap = request.getParameterMap();
        List<String> selectedIds = new ArrayList<>();

    	boolean check = ServletFileUpload.isMultipartContent(request);
    	if(check) {
    		DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            List<FileItem> items = upload.parseRequest(request);
            
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    if (fieldName.startsWith("checked_")) {
                        selectedIds.add(fieldName.replace("checked_", ""));
                    }
                }
            }
    	}

        if (selectedIds.isEmpty()) {
            request.setAttribute("errorMsg", "삭제할 상품을 선택해주세요.");
            return "redirect:/admin";
        }
        
        int successCount = 0;
        for (String id : selectedIds) {
            successCount += manager.removeProduct(id);
        }
        
        if (successCount != selectedIds.size()) {
            request.setAttribute("errorMsg", "일부 상품 삭제에 실패했습니다.");
        }
        
        return "redirect:/admin";
    }
}
