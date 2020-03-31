package com.internousdev.galaxy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.galaxy.dto.ProductInfoDTO;
import com.internousdev.galaxy.util.DBConnector;

//		検索条件が categoryId == 1 (全てのカテゴリー) の場合に使用する
		public List<ProductInfoDTO> getFromAllProductInfo(List<String> searchWordList) {

			DBConnector db = new DBConnector();
			Connection con = db.getConnection();

			List<ProductInfoDTO> productInfoList = new ArrayList<ProductInfoDTO>();

			String sql = "SELECT * FROM product_info";

//			検索ワードが指定されている場合のみ、for文を実行する
			if(!"".equals(searchWordList.get(0))) {

				for (int i = 0; i < searchWordList.size(); i++) {
					if (i == 0) {
						sql += " WHERE (product_name LIKE '%" + searchWordList.get(i) + "%' OR product_name_kana LIKE '%" + searchWordList.get(i) + "%')";
					} else {
						sql += " OR (product_name LIKE '%" + searchWordList.get(i) + "%' OR product_name_kana LIKE'%" + searchWordList.get(i) + "%')";
					}
				}
			}

			sql += " ORDER BY product_id ASC";

			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next()) {
					ProductInfoDTO dto = new ProductInfoDTO();
					dto.setProductId(rs.getInt("product_id"));
					dto.setProductName(rs.getString("product_name"));
					dto.setProductNameKana(rs.getString("product_name_kana"));
					dto.setImageFilePath(rs.getString("image_file_path"));
					dto.setImageFileName(rs.getString("image_file_name"));
					dto.setPrice(rs.getInt("price"));
					productInfoList.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();

			} finally {

				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}

			return productInfoList;

		}

//		検索条件が categoryId != 1 の場合に使用する
		public List<ProductInfoDTO> getProductInfo(int categoryId, List<String> searchWordList) {

			DBConnector db = new DBConnector();
			Connection con = db.getConnection();

			List<ProductInfoDTO> productInfoList = new ArrayList<ProductInfoDTO>();

			String sql = "SELECT * FROM product_info WHERE category_id = ?";

//			検索ワードが指定されている場合のみ、for文を実行する
			if(!"".equals(searchWordList.get(0))) {

				for (int i = 0; i < searchWordList.size(); i++) {
					if (i == 0) {
						sql += " AND ((product_name LIKE '%" + searchWordList.get(i) + "%' OR product_name_kana LIKE '%" + searchWordList.get(i) + "%')";
					} else {
						sql += " OR (product_name LIKE '%" + searchWordList.get(i) + "%' OR product_name_kana LIKE '%" + searchWordList.get(i) + "%')";
					}
				}
				sql += ")";
			}

			sql += " ORDER BY product_id ASC";

			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, categoryId);
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					ProductInfoDTO dto = new ProductInfoDTO();
					dto.setProductId(rs.getInt("product_id"));
					dto.setProductName(rs.getString("product_name"));
					dto.setProductNameKana(rs.getString("product_name_kana"));
					dto.setImageFilePath(rs.getString("image_file_path"));
					dto.setImageFileName(rs.getString("image_file_name"));
					dto.setPrice(rs.getInt("price"));
					productInfoList.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();

			} finally {

				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}

			return productInfoList;

		}

}