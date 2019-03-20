package com.bw.project_demo.ui.fragment.wode.fragment.person.PersonPresenter;

import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonBean;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonConstance.PersonConstance;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonModel.PersonModelImpl;

public class PersonPresenterImpl implements PersonConstance.PersonPresenter {
    PersonConstance.PersonView personView;
    private PersonConstance.PersonModel model;

    @Override
    public void attagView(PersonConstance.PersonView personView) {
        this.personView=personView;
        model = new PersonModelImpl();
    }

    @Override
    public void requestModel(int userId, String sessionId) {
        model.PersonresponseModel(userId,sessionId,new PersonConstance.PersonModel.PersonCallBack() {
            @Override
            public void getPersonData(PersonBean personBean) {
                personView.showData(personBean);
            }
        });
    }
}
