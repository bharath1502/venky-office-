package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.UsersRoleDao;
import com.chatak.pg.acq.dao.model.PGUserRoles;
import com.chatak.pg.acq.dao.model.QPGUserRoles;
import com.chatak.pg.acq.dao.repository.UsersRoleRepository;
import com.chatak.pg.model.UserRolesDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("usersRoleDao")
public class UsersRoleDaoImpl implements UsersRoleDao {
	@Autowired
	UsersRoleRepository usersRoleRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PGUserRoles saveRole(PGUserRoles pGUserRoles) {
		return usersRoleRepository.save(pGUserRoles);
	}

	public List<UserRolesDTO> searchRoles(UserRolesDTO userRolesDTO) {
		Integer pageIndex = userRolesDTO.getPageIndex();
		Integer pageSize = userRolesDTO.getPageSize();
		Integer offset = 0;
		Integer limit = 0;
		Integer totalRecords;
		List<UserRolesDTO> userRoleList = new ArrayList<>();

		if (pageIndex == null || pageIndex == 1) {
			totalRecords = getTotalNumberOfRecords(userRolesDTO);
			userRolesDTO.setNoOfRecords(totalRecords);
		}

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}

		JPAQuery query = new JPAQuery(entityManager);
		QPGUserRoles pgUserRoles = QPGUserRoles.pGUserRoles;
		List<Tuple> dataList = query
				.from(pgUserRoles)
				.where(isRoleName(userRolesDTO.getRoleName()),
						isRoleType(userRolesDTO.getRoleType()),
               isStatus(userRolesDTO.getStatus()),
               isStatusNotEq()
				)
				.offset(offset)
				.limit(limit)
				.orderBy(orderByRoleIdDesc())
				.list(QPGUserRoles.pGUserRoles.roleId,
						QPGUserRoles.pGUserRoles.roleName,
						QPGUserRoles.pGUserRoles.roleType,
						QPGUserRoles.pGUserRoles.status);
		UserRolesDTO userData = null;
		for (Tuple tuple : dataList) {
			userData = new UserRolesDTO();

			userData.setRoleId(tuple.get(pgUserRoles.roleId));
			userData.setRoleName(tuple.get(pgUserRoles.roleName));
			userData.setRoleType(tuple.get(pgUserRoles.roleType));
			userData.setStatus(tuple.get(pgUserRoles.status));
			userRoleList.add(userData);
		}

		return userRoleList;
	}

	private BooleanExpression isRoleName(String roleName) {
		return (roleName != null && !"".equals(roleName)) ? QPGUserRoles.pGUserRoles.roleName
				.toUpperCase().like("%" + roleName.toUpperCase().replace("*", "") + "%") : null;
	}
	
	private BooleanExpression isRoleType(String roleType) {
		return (roleType != null && !"".equals(roleType)) ? QPGUserRoles.pGUserRoles.roleType
				.toUpperCase().like("%" + roleType.toUpperCase().replace("*", "") + "%") : null;
	}
	
	private BooleanExpression isStatus(Integer status)
	{
		return (status != null ) ? QPGUserRoles.pGUserRoles.status.eq(status) : null;
	}
	
	  private BooleanExpression isStatusNotEq(){
		  return(QPGUserRoles.pGUserRoles.status.ne(1));
	  }


	private int getTotalNumberOfRecords(UserRolesDTO userRolesDTO) {
		JPAQuery query = new JPAQuery(entityManager);
		List<PGUserRoles> roleList = query.from(QPGUserRoles.pGUserRoles)
				.where(isRoleName(userRolesDTO.getRoleName()),
						isRoleType(userRolesDTO.getRoleType()),
						isStatus(userRolesDTO.getStatus()),isStatusNotEq()).list(QPGUserRoles.pGUserRoles);

		return (roleList != null && !roleList.isEmpty() ? roleList.size() : 0);
	}

	private OrderSpecifier<Long> orderByRoleIdDesc() {
		return QPGUserRoles.pGUserRoles.roleId.desc();
	}

	@Override
	public PGUserRoles getRoleOnRoleName(String roleName) {
		return usersRoleRepository.findByRoleName(roleName);
	}

	@Override
	public List<UserRolesDTO> getRoles() {
		List<UserRolesDTO> userRoleListData = new ArrayList<>();
		List<PGUserRoles> userRoleList = usersRoleRepository.findByRoleTypeAndStatus("Admin",0);
		UserRolesDTO userData = null;
		if (StringUtils.isListNotNullNEmpty(userRoleList)) {
			for (PGUserRoles pgUseRole : userRoleList) {
				userData = new UserRolesDTO();

				userData.setRoleId(pgUseRole.getRoleId());
				userData.setRoleName(pgUseRole.getRoleName());

				userRoleListData.add(userData);
			}
		}

		return userRoleListData;
	}

	@Override
	public PGUserRoles findByRoleId(Long roleId) {
		return usersRoleRepository.findByRoleId(roleId);
		
	}
	@Override
	public boolean isActiveRole(Long roleId){
		PGUserRoles activeUser=usersRoleRepository.findByRoleIdAndStatus(roleId,Constants.ACTIVE_STATUS);
		if(null!=activeUser){
			return true;
		}
		return false;
	}
}
