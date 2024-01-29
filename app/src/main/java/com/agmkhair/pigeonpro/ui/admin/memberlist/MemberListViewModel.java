package com.agmkhair.pigeonpro.ui.admin.memberlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.agmkhair.pigeonpro.ui.model.Member;
import java.util.List;

public class MemberListViewModel extends AndroidViewModel
{

    private MemberListRepository repository;
    public MemberListViewModel(@NonNull Application application)
    {
        super(application);
        repository = new MemberListRepository(application);
    }

    public LiveData<List<Member>> getMembers(MemberListPresenter presenter)
    {
        return repository.getMemberList(presenter);
    }
}