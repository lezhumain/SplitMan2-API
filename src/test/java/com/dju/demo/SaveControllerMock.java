package com.dju.demo;

import com.dju.demo.services.IDataService;

public class SaveControllerMock extends SaveController {
    private final int _testUserID;

    public SaveControllerMock(int testUserID) {
        super(new IDataServiceMock("[]"));
        _testUserID = testUserID;
    }

    public SaveControllerMock(int testUserID, final String all) {
        super(new IDataServiceMock(all));
        _testUserID = testUserID;
    }

    @Override
    protected int checkUserID(String fooCookie) {
        return this._testUserID;
    }

    public IDataService get_service() {
        return _service;
    }
}
