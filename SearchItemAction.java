package com.internousdev.galaxy.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.galaxy.dao.MCategoryDAO;
import com.internousdev.galaxy.dao.ProductInfoDAO;
import com.internousdev.galaxy.dto.MCategoryDTO;
import com.internousdev.galaxy.dto.ProductInfoDTO;
import com.internousdev.galaxy.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware {

	private int categoryId;
	private String searchWord;
	private List<String> errorMessageList;
	private List<ProductInfoDTO> productInfoList;
	private Map<String, Object> session;

	public String execute() {

//		検索ワードが未入力の場合(スペースのみの場合も含む)、空文字に設定する
		if(StringUtils.isBlank(searchWord)) {
			searchWord = "";

//		検索ワードが入力されている場合、入力チェックを行う
		} else {
			InputChecker inputChecker = new InputChecker();
			errorMessageList = inputChecker.doCheck("検索ワード", searchWord, 0, 50, true, true, true, true, true, true);

//			エラーがあった場合、商品一覧画面に表示させる
			if (errorMessageList.size() > 0) {
				return SUCCESS;
			}

//			全角スペースを半角スペースに変換
			searchWord = searchWord.replaceAll("　"," ");
//			2個以上の半角スペースを1個の半角スペースに変換
			searchWord = searchWord.replaceAll("\\s{2,}"," ");
//			先頭と末尾のスペースを削除
			searchWord = searchWord.trim();
		}

//		カテゴリが表示されていない場合は、全てのカテゴリーが選択されているものとする
		if(categoryId == 0) {
			categoryId = 1;
		}

//		編集したキーワードをスペース区切りで分割
		List<String> searchWordList = Arrays.asList(searchWord.split(" "));

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();

		if(categoryId == 1) {
			productInfoList = productInfoDAO.getFromAllProductInfo(searchWordList);
		} else {
			productInfoList = productInfoDAO.getProductInfo(categoryId, searchWordList);
		}

//		カテゴリーを保持していない場合、カテゴリーを保持する
		if(!session.containsKey("mCategoryList")) {
			MCategoryDAO mCategoryDAO = new MCategoryDAO();
			List<MCategoryDTO> mCategoryList = new ArrayList<MCategoryDTO>();
			mCategoryList = mCategoryDAO.getAllCategory();
			session.put("mCategoryList", mCategoryList);
		}

		return SUCCESS;

	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public List<String> getErrorMessageList() {
		return errorMessageList;
	}

	public List<ProductInfoDTO> getProductInfoList() {
		return productInfoList;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
