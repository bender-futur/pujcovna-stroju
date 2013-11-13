package cz.muni.fi.pa165.pujcovnastroju.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import cz.muni.fi.pa165.pujcovnastroju.dao.SystemUserDAO;
import cz.muni.fi.pa165.pujcovnastroju.dto.SystemUserDTO;
import cz.muni.fi.pa165.pujcovnastroju.entity.SystemUser;
import cz.muni.fi.pa165.pujcovnastroju.entity.UserTypeEnum;
import cz.muni.fi.pa165.pujcovnastroju.service.SystemUserService;
import cz.muni.fi.pa165.pujcovnastroju.serviceimpl.SystemUserServiceImpl;

/**
 *
 * @author VojtechSchlemmer
 */
@RunWith(MockitoJUnitRunner.class)
public class SystemUserServiceTest extends AbstractTest {
    
    @Mock 
    SystemUserDAO mockUserDao;
    @InjectMocks 
    SystemUserService userService = new SystemUserServiceImpl();
    
    @Before
    public void initMock() {
	Mockito.when(mockUserDao.create(Matchers.any(SystemUser.class))).thenAnswer(
                new Answer<SystemUser>() {
		@Override
		public SystemUser answer(InvocationOnMock inv) throws Throwable {
		    Object[] args = inv.getArguments();
		    return (SystemUser)args[0];
		}
	    });
	Mockito.when(mockUserDao.create(null)).thenThrow(
                new IllegalArgumentException("Error occured during storing user."));
	
	Mockito.when(mockUserDao.update(Matchers.any(SystemUser.class))).thenAnswer(
                new Answer<SystemUser>() {
		@Override
		public SystemUser answer(InvocationOnMock inv) throws Throwable {
		    Object[] args = inv.getArguments();
		    return (SystemUser)args[0];
		}
	    });
	Mockito.when(mockUserDao.update(null)).thenThrow(
                new IllegalArgumentException("Error occured during updating user."));
	
	Mockito.when(mockUserDao.read((long)1)).thenReturn(null);
	Mockito.when(mockUserDao.read((long)2)).thenReturn(new SystemUser());
	Mockito.when(mockUserDao.read(null)).thenThrow(
                new IllegalArgumentException("Error occured during reading user."));
	
	Mockito.when(mockUserDao.delete(Matchers.any(SystemUser.class))).thenReturn(new SystemUser());
	Mockito.when(mockUserDao.delete(null)).thenThrow(
                new IllegalArgumentException("Error occured during deleting user."));
    }
    
    @Test
    public void testCreate() {
	SystemUserDTO userDTO = null;
	SystemUserDTO userDTOProcessed = null;
	
	try {
	    userService.create(userDTO);
	    assertNotNull(userDTO); //if the exception is not thrown, test doesn't pass
	} catch (DataAccessException e) {
	    assertNull(userDTO);
	}
	
	userDTO = new SystemUserDTO();
	userDTOProcessed = userService.create(userDTO);
	assertNotNull(userDTOProcessed);
	assertEquals(userDTO, userDTOProcessed);
    }
    
    @Test
    public void testUpdate() {
	SystemUserDTO userDTO = null;
	SystemUserDTO userDTOProcessed = null;
	
	try {
	    userService.update(userDTO);
	    assertNotNull(userDTO); //if the exception is not thrown, test doesn't pass
	} catch (DataAccessException e) {
	    assertNull(userDTO);
	}
	
	userDTO = new SystemUserDTO();
	userDTOProcessed = userService.update(userDTO);
	assertNotNull(userDTOProcessed);
	assertEquals(userDTO, userDTOProcessed);
    }
    
    @Test
    public void testRead() {
	Long id = null;
	SystemUserDTO userDTOProcessed = null;
	
	try {
	    userService.read(id);
	    assertNotNull(id); //if the exception is not thrown, test doesn't pass
	} catch (DataAccessException e) {
	    assertNull(id);
	}
	
	id = (long)1;
	userDTOProcessed = userService.read(id);
	assertNull(userDTOProcessed);
	
	id = (long)2;
	userDTOProcessed = userService.read(id);
	assertNotNull(userDTOProcessed);
    }
    
    @Test
    public void testDelete() {
	SystemUserDTO userDTO = null;
	SystemUserDTO userDTOProcessed = null;
	
	try {
	    userService.delete(userDTO);
	    assertNotNull(userDTO); //if the exception is not thrown, test doesn't pass
	} catch (DataAccessException e) {
	    assertNull(userDTO);
	}
	
	userDTO = new SystemUserDTO();
	userDTOProcessed = userService.delete(userDTO);
	assertNotNull(userDTOProcessed);
    }
    
    @Test
    public void testFindAllSystemUsers() {
	List<SystemUserDTO> userDTOs = null;
	List<SystemUser> userList = new ArrayList<>();
	userList.add(new SystemUser());
	userList.add(new SystemUser());
	
	Mockito.when(mockUserDao.findAllSystemUsers()).thenReturn(null);
	
	userDTOs = userService.findAllSystemUsers();
	assertNull(userDTOs);
	
	Mockito.when(mockUserDao.findAllSystemUsers()).thenReturn(userList);
	
	userDTOs = userService.findAllSystemUsers();
	assertEquals(userDTOs.size(), 2);
	
	userList.add(new SystemUser());
	userDTOs = userService.findAllSystemUsers();
	assertEquals(userDTOs.size(), 3);
    }
    
    @Test
    public void testGetLoansByParams() {
	List<SystemUser> userList = new ArrayList<>();
	userList.add(new SystemUser());
	userList.add(new SystemUser());
	
	Mockito.when(mockUserDao.getSystemUsersByParams(
                Matchers.any(String.class), 
                Matchers.any(String.class), 
                Matchers.any(UserTypeEnum.class))).thenReturn(null);
	
	Mockito.when(mockUserDao.getSystemUsersByParams(
                Matchers.any(String.class), 
                Matchers.any(String.class), 
                Matchers.any(UserTypeEnum.class))).thenReturn(userList);
    }
    
}