package net.oschina.app.improve.git.api;

import android.annotation.SuppressLint;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import net.oschina.app.api.ApiHttpClient;
import net.oschina.app.improve.git.bean.Project;

/**
 * Created by haibin
 * on 2017/3/9.
 */
@SuppressLint("DefaultLocale")
public final class API {
    private static AsyncHttpClient mClient = new AsyncHttpClient();

    static {
        mClient.setURLEncodingEnabled(false);
    }

    /**
     * 获取码云推荐列表
     *
     * @param page    page=1、2、3
     * @param handler 回调
     */
    public static void getFeatureProjects(int page, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        mClient.get("http://git.oschina.net/api/v3/projects/featured/osc", params, handler);
    }

    /**
     * 获取项目详情
     *
     * @param id      项目id
     * @param handler 回调
     */
    public static void getProjectDetail(long id, TextHttpResponseHandler handler) {
        mClient.get(String.format("http://git.oschina.net/api/v3/projects/%d/osc", id), handler);
    }

    /**
     * 获取项目详情
     *
     * @param pathWithNamespace 项目pathWithNamespace
     * @param handler           回调
     */
    public static void getProjectDetail(String pathWithNamespace, TextHttpResponseHandler handler) {
        mClient.get(String.format("http://git.oschina.net/api/v3/projects/%s/osc", pathWithNamespace), handler);
    }

    /**
     * 获取代码仓库
     *
     * @param id      项目id
     * @param path    仓库的相对路径 如：app/src/main
     * @param refName 分支或者标签名称，默认为master分支
     * @param handler 回调
     */
    public static void getCodeTree(long id, String path, String refName, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("path", path);
        params.put("ref_name", refName);
        mClient.get(String.format("http://git.oschina.net/api/v3/projects/%d/repository/tree", id), params, handler);
    }

    /**
     * 获取代码详情
     *
     * @param id       项目id
     * @param filePath 仓库的相对路径 如：app/src/main
     * @param ref      分支或者标签名称，默认为master分支
     * @param handler  回调
     */
    public static void getCodeDetail(long id, String filePath, String ref, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("file_path", filePath);
        params.put("ref", ref);
        mClient.get(String.format("http://git.oschina.net/api/v3/projects/%d/repository/files", id), params, handler);
    }

    /**
     * 获取项目分支
     *
     * @param id      项目id
     * @param handler 回调
     */
    public static void getProjectBranches(long id, TextHttpResponseHandler handler) {
        mClient.get(String.format("http://git.oschina.net/api/v3/projects/%d/repository/branches", id), handler);
    }

    /**
     * 获取项目评论
     *
     * @param id      项目id
     * @param handler 回调
     */
    public static void getProjectComments(long id, String token, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("projectId", id);
        params.put("pageToken", token);
        ApiHttpClient.get("action/apiv2/git_comments_list", params, handler);
    }

    /**
     * 添加git项目评论
     *
     * @param project 项目
     * @param comment 内容
     * @param handler 回调
     */
    public static void addProjectComment(Project project, String comment, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("projectId", project.getId());
        params.put("name", project.getName());
        params.put("pathWithNamespace", project.getPathWithNamespace());
        params.put("content", comment);
        ApiHttpClient.post("action/apiv2/pub_git_comment", params, handler);
    }

    /**
     * 获取项目评论数
     *
     * @param id      项目id
     * @param handler 回调
     */
    public static void getProjectCommentCount(long id, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("projectId", id);
        ApiHttpClient.get("action/apiv2/git_comments_count", params, handler);
    }
}
