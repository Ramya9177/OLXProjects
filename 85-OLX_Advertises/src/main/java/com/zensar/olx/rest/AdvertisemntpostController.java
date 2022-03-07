package com.zensar.olx.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zensar.olx.bean.AdvertisementPost;
import com.zensar.olx.bean.AdvertisementStatus;
import com.zensar.olx.bean.Category;
import com.zensar.olx.bean.FilterCriteriaRequest;
import com.zensar.olx.bean.NewAdvertisementPostRequest;
import com.zensar.olx.bean.NewAdvertisementPostResponse;
import com.zensar.olx.bean.OlxUser;
import com.zensar.olx.service.AdvertisementPostService;

@RestController
public class AdvertisemntpostController {

	@Autowired
	AdvertisementPostService service;

	// 8 posting new advertises
	@PostMapping("/advertise/{un}")
	public NewAdvertisementPostResponse add(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "un") String userName) {

		AdvertisementPost post = new AdvertisementPost();

		post.setTitle(request.getTitle());
		post.setPrice(request.getPrice());
		post.setDescription(request.getDescription());

		int categoryId = request.getCategoryId();

		RestTemplate restTemplete = new RestTemplate();
		Category category;
		String url = "http://localhost:9052/advertise/getCategoryId/" + categoryId;
		category = restTemplete.getForObject(url, Category.class);// getforobject-->connect with microservice

		post.setCategory(category);// store in post object

		url = "http://localhost:9051/user/find/" + userName;// to connect with user service
		OlxUser olxUser = restTemplete.getForObject(url, OlxUser.class);
		post.setOlxUser(olxUser);

		AdvertisementStatus advertisementStatus = new AdvertisementStatus(1, "Open");// new should always will be open
																						// we hardcoded
		post.setAdvertisementStatus(advertisementStatus);

		AdvertisementPost advertisementPost = this.service.addAdvertisement(post);// entity saved to db

		NewAdvertisementPostResponse response = new NewAdvertisementPostResponse();

		response.setId(advertisementPost.getId());
		response.setTitle(advertisementPost.getTitle());
		response.setPrice(advertisementPost.getPrice());
		response.setCategory(advertisementPost.getCategory().getName());
		response.setDescription(advertisementPost.getDescription());
		response.setUserName(advertisementPost.getOlxUser().getUserName());
		response.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
		response.setCreatedDate(advertisementPost.getCreatedDate());
		response.setModifiedDate(advertisementPost.getModifiedDate());

		return response;
	}

	// 9 updating advertise with id
	@PutMapping("/advertise/{aid}/{userName}")
	public NewAdvertisementPostResponse f2(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "aid") int id, @PathVariable(name = "userName") String userName) {

		AdvertisementPost post = this.service.getAdvertisemntById(id);

		post.setTitle(request.getTitle());
		post.setDescription(request.getDescription());
		post.setPrice(request.getPrice());

		RestTemplate restTemplete = new RestTemplate();

		Category category;
		String url = "http://localhost:9052/advertise/getCategoryId/" + request.getCategoryId();
		category = restTemplete.getForObject(url, Category.class);
		post.setCategory(category);

		url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplete.getForObject(url, OlxUser.class);
		post.setOlxUser(olxUser);

		url = "http://localhost:9052/advertisement/status/" + request.getStatusId();
		AdvertisementStatus advertisementStatus;
		advertisementStatus = restTemplete.getForObject(url, AdvertisementStatus.class);
		post.setAdvertisementStatus(advertisementStatus);

		AdvertisementPost advertisementPost = this.service.updateAdvertisement(post); // writing in db

		NewAdvertisementPostResponse postResponse;
		postResponse = new NewAdvertisementPostResponse();

		postResponse.setId(advertisementPost.getId());
		postResponse.setTitle(advertisementPost.getTitle());
		postResponse.setDescription(advertisementPost.getDescription());
		postResponse.setPrice(advertisementPost.getPrice());
		postResponse.setUserName(advertisementPost.getOlxUser().getUserName());
		postResponse.setCategory(advertisementPost.getCategory().getName());
		postResponse.setCreatedDate(advertisementPost.getCreatedDate());
		postResponse.setModifiedDate(advertisementPost.getModifiedDate());
		postResponse.setStatus(advertisementPost.getAdvertisementStatus().getStatus());

		return postResponse;

	}

//10 get all the records
	@GetMapping("/user/advertise/{userName}")
	public List<NewAdvertisementPostResponse> f3(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "userName") String userName) {
		List<AdvertisementPost> allPosts = this.service.getAllAdvertisements();

		RestTemplate restTemplete = new RestTemplate();

		List<AdvertisementPost> filteredPosts = new ArrayList<>();

		for (AdvertisementPost post : allPosts) {

			String url = "http://localhost:9051/user/find/" + userName;
			OlxUser olxUser = restTemplete.getForObject(url, OlxUser.class);
			// post.setOlxUser(olxUser);

			Category category;
			url = "http://localhost:9052/advertise/getCategoryId/" + post.getCategory().getId();
			category = restTemplete.getForObject(url, Category.class);
			post.setCategory(category);

			url = "http://localhost:9052/advertisement/status/" + post.getAdvertisementStatus().getId();
			AdvertisementStatus advertisementStatus;
			advertisementStatus = restTemplete.getForObject(url, AdvertisementStatus.class);
			post.setAdvertisementStatus(advertisementStatus);

			if (olxUser.getOlxUserId() == post.getOlxUser().getOlxUserId()) {
				post.setOlxUser(olxUser);
				filteredPosts.add(post);

			}
		}
		List<NewAdvertisementPostResponse> responseList = new ArrayList<>();
		for (AdvertisementPost advertisementPost : filteredPosts) {

			NewAdvertisementPostResponse postResponse;
			postResponse = new NewAdvertisementPostResponse();

			postResponse.setId(advertisementPost.getId());
			postResponse.setTitle(advertisementPost.getTitle());
			postResponse.setDescription(advertisementPost.getDescription());
			postResponse.setPrice(advertisementPost.getPrice());
			postResponse.setUserName(advertisementPost.getOlxUser().getUserName());
			postResponse.setCategory(advertisementPost.getCategory().getName());
			postResponse.setCreatedDate(advertisementPost.getCreatedDate());
			postResponse.setModifiedDate(advertisementPost.getModifiedDate());
			postResponse.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
			responseList.add(postResponse);
		}
		System.out.println(responseList);
		return responseList;
	}

//11 get specific record with id
	@GetMapping("/users/advertise/{advertiseId}")
	public NewAdvertisementPostResponse f4(@PathVariable(name = "advertiseId") int advertiseId) {

		AdvertisementPost advertisementPost = service.getAdvertisemntById(advertiseId);

		System.out.println(advertisementPost);
		RestTemplate restTemplate = new RestTemplate();

		Category category;
		String url = "http://localhost:9052/advertise/getCategoryId/" + advertisementPost.getCategory().getId();
		category = restTemplate.getForObject(url, Category.class);
		System.out.println(category);

		url = "http://localhost:9051/user/" + advertisementPost.getOlxUser().getOlxUserId();
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		System.out.println(olxUser);

		advertisementPost.setOlxUser(olxUser);
		url = "http://localhost:9052/advertisement/status/" + advertisementPost.getAdvertisementStatus().getId();
		AdvertisementStatus advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
		System.out.println(advertisementStatus);

		advertisementPost.setAdvertisementStatus(advertisementStatus);
		NewAdvertisementPostResponse response = new NewAdvertisementPostResponse();
		response.setId(advertisementPost.getId());
		response.setTitle(advertisementPost.getTitle());
		response.setPrice(advertisementPost.getPrice());
		response.setCategory(category.getName());
		response.setUserName(advertisementPost.getOlxUser().getUserName());
		response.setDescription(advertisementPost.getDescription());
		response.setCreatedDate(advertisementPost.getCreatedDate());
		response.setModifiedDate(advertisementPost.getModifiedDate());
		response.setStatus(advertisementStatus.getStatus());
		return response;
	}

//12 delete the record using id
	@DeleteMapping("/user/advertise/{advertiseId}")
	public boolean f5(@PathVariable(name = "advertiseId") int id) {
		AdvertisementPost advertisementPost = this.service.getAdvertisemntById(id);
		System.out.println(advertisementPost);
		return this.service.deleteAdvertisementPost(advertisementPost);
	}
	
	@GetMapping("/advertise/search/filtercriteria/{search}")
	public List<NewAdvertisementPostResponse> f7(@RequestBody FilterCriteriaRequest criteriaRequest,@PathVariable(name="search")String searchText) {
	LocalDate dateFrom=criteriaRequest.getFromDate();
	LocalDate toDdate=criteriaRequest.getToDate();
	List<AdvertisementPost>allPost=this.service.getAllAdvertisements();
	System.out.println(allPost);
	RestTemplate restTemplate=new RestTemplate();
	for(AdvertisementPost advertisementPost:allPost)
	{
	String url = null;
	url = "http://localhost:9051/user/" + advertisementPost.getOlxUser().getOlxUserId();
	OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
	advertisementPost.setOlxUser(olxUser);

	 Category category;
	url = "http://localhost:9052/advertise/getCategoryId/" + advertisementPost.getCategory().getId();
	category = restTemplate.getForObject(url, Category.class);
	advertisementPost.setCategory(category);

	 url = "http://localhost:9052/advertisement/status/" + advertisementPost.getAdvertisementStatus().getId();
	AdvertisementStatus advertisementStatus;
	advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
	advertisementPost.setAdvertisementStatus(advertisementStatus);
	}
	List<AdvertisementPost>filterPosts=new ArrayList<>();
	for(AdvertisementPost advertisementPost:allPost)
	{
	if((advertisementPost.getCategory().getName().toLowerCase().contains(searchText.toLowerCase()))||
	(advertisementPost.getTitle().toLowerCase().contains(searchText.toLowerCase()))
	||(advertisementPost.getDescription().toLowerCase().contains(searchText.toLowerCase()))||
	(advertisementPost.getAdvertisementStatus().getStatus().toLowerCase().contains(searchText.toLowerCase()))
	)
	{
	filterPosts.add(advertisementPost);
	}
	}
	List<NewAdvertisementPostResponse> responce=new ArrayList<>();
	for(AdvertisementPost advertisementPost:filterPosts)
	{
	NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();

	 postRespone.setId(advertisementPost.getId());
	postRespone.setTitle(advertisementPost.getTitle());
	postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
	postRespone.setDescription(advertisementPost.getDescription());
	postRespone.setPrice(advertisementPost.getPrice());
	postRespone.setCategory(advertisementPost.getCategory().getName());
	postRespone.setCreatedDate(advertisementPost.getCreatedDate());
	postRespone.setModifiedDate(advertisementPost.getModifiedDate());
	postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
	responce.add(postRespone);
	}
	return responce;
	}
	@GetMapping("/advertise/{search}")
	public List<NewAdvertisementPostResponse> f7(@PathVariable(name="search")String searchText) {
	List<AdvertisementPost>allPost=this.service.getAllAdvertisements();
	System.out.println(allPost);
	RestTemplate restTemplate=new RestTemplate();
	for(AdvertisementPost advertisementPost:allPost)
	{
	String url = null;
	url = "http://localhost:9051/user/" + advertisementPost.getOlxUser().getOlxUserId();
	OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
	advertisementPost.setOlxUser(olxUser);

	 Category category;
	url = "http://localhost:9052/advertise/getCategory/" + advertisementPost.getCategory().getId();
	category = restTemplate.getForObject(url, Category.class);
	advertisementPost.setCategory(category);

	 url = "http://localhost:9052/advertisement/status/"+ advertisementPost.getAdvertisementStatus().getId();
	AdvertisementStatus advertisementStatus;
	advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
	advertisementPost.setAdvertisementStatus(advertisementStatus);
	}
	List<AdvertisementPost>filterPosts=new ArrayList<>();
	for(AdvertisementPost advertisementPost:allPost)
	{
	if((advertisementPost.getCategory().getName().toLowerCase().contains(searchText.toLowerCase()))||
	(advertisementPost.getTitle().toLowerCase().contains(searchText.toLowerCase()))
	||(advertisementPost.getDescription().toLowerCase().contains(searchText.toLowerCase()))||
	(advertisementPost.getAdvertisementStatus().getStatus().toLowerCase().contains(searchText.toLowerCase()))
	)
	{
	filterPosts.add(advertisementPost);
	}
	}
	List<NewAdvertisementPostResponse> responce=new ArrayList<>();
	for(AdvertisementPost advertisementPost:filterPosts)
	{
	NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();

	 postRespone.setId(advertisementPost.getId());
	postRespone.setTitle(advertisementPost.getTitle());
	postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
	postRespone.setDescription(advertisementPost.getDescription());
	postRespone.setPrice(advertisementPost.getPrice());
	postRespone.setCategory(advertisementPost.getCategory().getName());
	postRespone.setCreatedDate(advertisementPost.getCreatedDate());
	postRespone.setModifiedDate(advertisementPost.getModifiedDate());
	postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
	responce.add(postRespone);
	}
	return responce;
	}
	@GetMapping("/advertise/{advertiseId}")
	public NewAdvertisementPostResponse f6(@PathVariable(name="advertiseId") int id) {
	
		AdvertisementPost advertisementPost=this.service.getAdvertisemntById(id);
		
		return null;
	}
}
