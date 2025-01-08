package controller.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;
import model.domain.Product;
import model.service.ProductManager;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import java.io.File;
import java.util.*;

public class UpdateProductController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProductManager manager = ProductManager.getInstance();
        
        
        if (!ServletFileUpload.isMultipartContent(request)) {
            return "redirect:/admin";
        }
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String uploadPath = request.getServletContext().getRealPath("/upload");
        factory.setRepository(new File(uploadPath));
        
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> formItems = upload.parseRequest(request);
        
        Map<String, String> parameters = new HashMap<>();
        Map<String, FileItem> files = new HashMap<>();
        
        for (FileItem item : formItems) {
            if (item.isFormField()) {
                parameters.put(item.getFieldName(), item.getString("UTF-8"));
            } else {
                files.put(item.getFieldName(), item);
            }
        }
        
        List<String> selectedIds = new ArrayList<>();
        for (String paramName : parameters.keySet()) {
            if (paramName.startsWith("checked_")) {
                selectedIds.add(paramName.replace("checked_", ""));
            }
        }
        
        for (String selectedId : selectedIds) {
            FileItem thumbnailFile = files.get("thumb_" + selectedId);
            String thumbnailPath = "img";
            if (thumbnailFile != null && !thumbnailFile.getName().isEmpty()) {
                String originalFileName = new File(thumbnailFile.getName()).getName();
                String fileName = UUID.randomUUID().toString() + "_" 
                                         + originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
                File storeFile = new File(uploadPath + File.separator + fileName);
                thumbnailFile.write(storeFile);
                thumbnailPath = fileName;
            }
            
            Product product = new Product(
                Integer.parseInt(parameters.get("id_" + selectedId)),
                parameters.get("name_" + selectedId),
                thumbnailPath,
                parameters.get("description_" + selectedId),
                Integer.parseInt(parameters.get("price_" + selectedId).replace("원", "")),
                Integer.parseInt(parameters.get("stock_" + selectedId)),
                Integer.parseInt(parameters.get("peopleCategory_" + selectedId)),
                Integer.parseInt(parameters.get("foodTypeCategory_" + selectedId))
            );
        
            manager.updateProduct(product);
        }
        
        return "redirect:/admin";
    }
}