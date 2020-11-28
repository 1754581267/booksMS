package bao.xy.service;

import bao.xy.model.Staff;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    Integer login(Staff user, HttpServletRequest request);
}
