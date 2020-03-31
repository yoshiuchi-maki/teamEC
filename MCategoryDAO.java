package com.internousdev.galaxy.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.galaxy.dto.MCategoryDTO;
import com.internousdev.galaxy.util.DBConnector;

public class MCategoryDAO {

//	すべてのカテゴリーを取得する
	public List<MCategoryDTO> getAllCategory() {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		List<MCategoryDTO> mCategoryList = new ArrayList<MCategoryDTO>();

		String sql = "SELECT category_id, category_name FROM m_category";

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				MCategoryDTO dto = new MCategoryDTO();
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setCategoryName(rs.getString("category_name"));
				mCategoryList.add(dto);
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

		return  mCategoryList;

	}

}