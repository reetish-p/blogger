# Blogging App

## Features

- Users can signup to create accounts
    - Users have username, email, bio , image
- Users can login to their accounts
- Users can write blog articles
    - Articles have a title, description, tags and content body
    - Tags are an array of strings
    - Users can delete articles they have created
    - Users can update articles they have created
    - Can see list of tags
- Users can comment on blog articles
    - Comments will have a title and a body
    - Title is optional, but body is required
    - Users can delete comments they have created
- Users can like blog articles
- Users can follow and unfollow other users
- Users can see profile of individual users
- There should be a feed of all articles of authors a user is following

### Docker
  - docker pull reetish/blogger_v1:blogger_v1_tag
  - docker run -p 8080:8080 reetish/blogger_v1:blogger_v1_tag
### Postman Collection
  - https://www.postman.com/collections/a524e509846bcf73ac91