package controller.review;

import model.service.ProductManager;
import model.service.ReviewManager;
import model.domain.Product;
import model.domain.Review;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controller.Controller;
import controller.user.UserSessionUtils;

public class CreateReviewController implements Controller {
    private ReviewManager reviewManager;

    public CreateReviewController() {
        reviewManager = new ReviewManager();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        	int reviewId = Integer.parseInt(request.getParameter("reviewId"));
//        	System.out.println("\n\n reviewId==="+reviewId);
        	
        	int productId = 0;//Integer.parseInt(request.getParameter("productId")); // int productId = 1010;
//        	ProductManager manager = ProductManager.getInstance();
//            Product product = manager.getDetail(productId);
//            request.setAttribute("product", product);
//        	System.out.println("\n productId==="+productId);
        	
        	int orderId = 0;//Integer.parseInt(request.getParameter("orderId"));
//        	System.out.println("\n orderId==="+orderId);
        	int lineNo = 0;//Integer.parseInt(request.getParameter("lineNo"));
//        	System.out.println("\n lineNo==="+lineNo);
//
//			System.out.println("Logged-in OrderId ID: " + orderId);
            HttpSession session = request.getSession();
        	String nickname = (String)session.getAttribute(UserSessionUtils.USER_SESSION_KEY); // request.getParameter("nickname");
        	
            double rating = 0.0; //= Double.parseDouble(request.getParameter("rating"));
            String reviewText = null;// = request.getParameter("reviewText");
            String reviewImg = null;// = "example.jpg";//request.getParameter("reviewImg");
            // 업로드된 파일 처리
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
                	  if(item.getFieldName().equals("productId")) productId = Integer.parseInt(value);
                	  if(item.getFieldName().equals("orderId")) orderId = Integer.parseInt(value);
                	  if(item.getFieldName().equals("lineNo")) lineNo = Integer.parseInt(value);    
                      if(item.getFieldName().equals("rating")) rating = Double.parseDouble(value);
                      if(item.getFieldName().equals("reviewText")) reviewText = value;                
                     
                   }
                   else {
                      if(item.getFieldName().equals("reviewImg")) {
                         String oriFilename = item.getName();
                             if (oriFilename == null || oriFilename.trim().length() == 0) continue;
                         
                             reviewImg = UUID.randomUUID().toString() + "_" 
                                  + oriFilename.substring(oriFilename.lastIndexOf("\\") + 1);  
                           
                          System.out.println("uploaded file " + reviewImg);
                          File file = new File(dir, reviewImg);
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
          }
            
            ProductManager manager = ProductManager.getInstance();
            Product product = manager.getDetail(productId);
            request.setAttribute("product", product);
            
            // 기존 리뷰 확인
            int reviewId = reviewManager.findReviewId(productId, orderId);
            boolean isUpdated;
            if (reviewId != 0) {
                // 리뷰가 이미 존재하면 업데이트
                Review existingReview = reviewManager.getReviewById(reviewId);
                existingReview.setRating(rating);
                existingReview.setReviewText(reviewText);
                existingReview.setReviewImg(reviewImg);

                isUpdated = reviewManager.updateReview(existingReview);
            } else {
            	Review newReview = new Review(productId, orderId, lineNo, nickname, rating, reviewText, reviewImg);
                //boolean createResult = reviewManager.createReview(newReview);
                isUpdated = reviewManager.createReview(newReview);
            }
            
//            if (createResult) {
//                return "redirect:/purchase/purchaseList";
//            } else {
//                request.setAttribute("createResult", createResult);
//                return "/review/reviewForm.jsp";
//            }
            if (isUpdated) {
                return "redirect:/purchase/purchaseList/orderId?orderId="+orderId;
            } else {
            	request.setAttribute("isUpdated", isUpdated);
                request.setAttribute("errorMessage", "리뷰 저장에 실패했습니다. 다시 시도해 주세요.");
                return "/review/reviewForm.jsp";
            }
    }
}