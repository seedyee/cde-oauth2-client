package io.cde.oauth2.client.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by liaofangcai.
 * Created time 12/12/16.
 */
@Repository
public class MongodbRepository {

    /**
     * mongodb操作实现.
     */
    @Autowired
    private MongoTemplate temPlate;

    /**
     * 将github用户基本数据存储到本系统.
     * @param account this account
     */
    public void addGitHubAccount(final String account) {
        temPlate.insert(account, "github_account");
    }

    /**
     * 检查用户github用户基本信息是否已经存在.
     * @param accountName this accountName
     * @param email this email
     * @return this boolean
     */
    public boolean checkAccount(final String accountName, final String email) {
        final boolean isuseInfoExisted;
        final Query query = Query.query(Criteria.where("login").is(accountName).orOperator(Criteria.where("email").is(email)));
        isuseInfoExisted = temPlate.exists(query, "github_account");
        return isuseInfoExisted;
    }
}
