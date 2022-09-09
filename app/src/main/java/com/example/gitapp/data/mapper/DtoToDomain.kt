package com.example.gitapp.data.mapper

import com.example.gitapp.data.dto.Owner
import com.example.gitapp.data.dto.UserRepoResponseDtoItem
import com.example.gitapp.data.dto.UserResponseDto
import com.example.gitapp.domain.models.Repo
import com.example.gitapp.domain.models.User

 fun UserResponseDto.toUser():User{
    return User(
        avatar_url, bio, blog, collaborators, company, created_at, disk_usage, email, events_url, followers, followers_url, following, following_url, gists_url, gravatar_id, hireable, html_url, id, location, login, name, node_id, organizations_url, owned_private_repos, private_gists, public_gists, public_repos, received_events_url, repos_url, site_admin, starred_url, subscriptions_url, total_private_repos, twitter_username, two_factor_authentication, type, updated_at, url
    )
}

fun UserRepoResponseDtoItem.toRepo():Repo{
    return Repo(
        archive_url, archived, assignees_url, blobs_url, branches_url, clone_url, collaborators_url, comments_url, commits_url, compare_url, contents_url, contributors_url, created_at, default_branch, deployments_url, description, disabled, downloads_url, events_url, fork, forks_count, forks_url, full_name, git_commits_url, git_refs_url, git_tags_url, git_url, has_downloads, has_issues, has_pages, has_projects, has_wiki, homepage, hooks_url, html_url, id, is_template, issue_comment_url, issue_events_url, issues_url, keys_url, labels_url, language, languages_url, merges_url, milestones_url, mirror_url, name, node_id, notifications_url, open_issues_count, owner, permissions, private, pulls_url, pushed_at, releases_url, size, ssh_url, stargazers_count, stargazers_url, statuses_url, subscribers_url, subscription_url, svn_url, tags_url, teams_url, template_repository, topics, trees_url, updated_at, url, visibility, watchers_count
    )
}