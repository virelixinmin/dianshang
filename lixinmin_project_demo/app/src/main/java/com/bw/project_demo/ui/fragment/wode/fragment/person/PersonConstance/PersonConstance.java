package com.bw.project_demo.ui.fragment.wode.fragment.person.PersonConstance;

import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonBean;

public interface PersonConstance {
    public interface PersonView{
        public void showData(PersonBean personBean);
    }
    public interface PersonPresenter<PersonView>{
        public void attagView(PersonConstance.PersonView personView);
        public void requestModel(int userId, String sessionId);
    }
    public interface PersonModel{
        public void PersonresponseModel(int userId, String sessionId, PersonCallBack personCallBack);
        public interface PersonCallBack{
            public void getPersonData(PersonBean personBean);
        }
    }
}

