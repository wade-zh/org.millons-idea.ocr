/***
 * @pName mi-ocr-web-app
 * @name User
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.app.entity.agent;

public class User {
    private Integer id;
    private String username;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(Integer id, String username, String password) {

        this.id = id;
        this.username = username;
        this.password = password;
    }
}