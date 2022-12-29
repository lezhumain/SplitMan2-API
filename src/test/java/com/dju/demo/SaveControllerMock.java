package com.dju.demo;

import com.dju.demo.services.IDataService;

public class SaveControllerMock extends SaveController {
    private final int _testUserID;

    public SaveControllerMock(int testUserID) {
        super();
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
