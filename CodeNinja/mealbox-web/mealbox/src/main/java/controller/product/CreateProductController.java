package controller.product;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controller.Controller;
import model.service.ProductManager;
import model.domain.Product;

public class CreateProductController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductManager manager = ProductManager.getInstance();
		
		String name = null;
		String thumb = null;
		String description = null;
		int price = 0;
		int stock = 0;
		int peopleCategory = 0;
		int foodTypeCategory = 0;
		
		Boolean check = ServletFileUpload.isMultipartContent(request);
		
		if(check) {
			ServletContext context = request.getServletContext();
			String path = context.getRealPath("/upload");
			File dir = new File(path);
			
			if(!dir.exists()) {
				dir.mkdir();
			}
			
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(10 * 1024);
				factory.setRepository(dir);
				
				ServletFileUpload upload = new ServletFileUpload(factory);
					// 업로드 파일 최대 용량 = 10MB
				upload.setSizeMax(10 * 1024 * 1024);
				upload.setHeaderEncoding("utf-8");

                List<FileItem> items = (List<FileItem>)upload.parseRequest(request); 
                
				for(int i=0; i<items.size(); ++i) {
					FileItem item = (FileItem)items.get(i);
					String value = item.getString("utf-8");
					
					if(item.isFormField()) {
						if(item.getFieldName().equals("newName")) name = value;
						if(item.getFieldName().equals("newDescription")) description = value;
						if(item.getFieldName().equals("newPrice")) price = Integer.parseInt(value);
						if(item.getFieldName().equals("newStock")) stock = Integer.parseInt(value);
						if(item.getFieldName().equals("newPeopleCategory")) peopleCategory = Integer.parseInt(value);
						if(item.getFieldName().equals("newFoodTypeCategory")) foodTypeCategory = Integer.parseInt(value);
					}
					else {
						if(item.getFieldName().equals("newThumb")) {
							String oriFilename = item.getName();
                			if (oriFilename == null || oriFilename.trim().length() == 0) continue;
							
							thumb = UUID.randomUUID().toString() + "_" 
            						+ oriFilename.substring(oriFilename.lastIndexOf("\\") + 1);  
        					
						    System.out.println("uploaded file " + thumb);
						    File file = new File(dir, thumb);
						    item.write(file);
						}
					}
				}
				
			} catch(SizeLimitExceededException e) {
				// 업로드 되는 파일의 크기가 지정된 최대 크기를 초과할 때 발생하는 예외처리
				e.printStackTrace();           
            } catch(FileUploadException e) {
            	// 파일 업로드와 관련되어 발생할 수 있는 예외 처리
                e.printStackTrace();
            } catch(Exception e) {            
                e.printStackTrace();
            }
			

			Product product = new Product(
						name, thumb, description, price, stock, 0,
						0.0, peopleCategory, foodTypeCategory);

			int res = manager.createProduct(product);
			if(res <= 0) {
				request.setAttribute("creation failed", product);
			}
		}

	    return "redirect:/admin";
	}

}