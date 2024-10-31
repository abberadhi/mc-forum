# bike-forum
Forum about motorcycles 

## TODO

### BE

* Sign up / sign in
* user profile - account summary and posts.

* create post, set tags
* comment posts
* reply to comments


API Operations

#### GET /api/user/{user_id}
Get user profile

#### GET /api/feed/page/{pagenumber}
Get latest posts on the feed

#### GET /api/post/{post_id}
get post by post_id

#### POST /api/post/
{
    [tags],
    title,
    content
}

#### POST /api/post/comment
comment on a post
{
    comment_id
    content
}

#### GET /api/post/{post_id}


### Database:

#### User table
id / username / date_joined / hashed password

#### post_entity table
id / title / content / user_id / date_created / date_updated / upvote_count / tags

#### tags_entity table
id / tag_name

#### comment_entity table
id / comment / comment_id / post_id / user_id / date_created / upvote_count

### Pipelines 

#### Tests ran

### Docker & compose for setup

### INSTALLATION / SETUP
