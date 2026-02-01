# Online Course Platform - NoSQL Database Project

## 📋 Project Overview

A comprehensive online learning platform built with MongoDB, Express.js, React, and Node.js (MERN stack). The platform enables instructors to create and manage courses while students can enroll, track progress, and rate courses.

### Team Members
- Student 1: [Name] - Backend Development, Database Design
- Student 2: [Name] - Frontend Development, API Integration

## 🏗️ System Architecture

### Technology Stack
- **Frontend**: React 18, React Router, Axios
- **Backend**: Node.js, Express.js
- **Database**: MongoDB with Mongoose ODM
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: bcryptjs for password hashing

### Architecture Diagram
```
┌─────────────┐      HTTP/REST       ┌──────────────┐      Mongoose      ┌──────────────┐
│   React     │ ◄──────────────────► │  Express.js  │ ◄─────────────────► │   MongoDB    │
│  Frontend   │    JSON/JWT Auth     │   Backend    │   CRUD Operations   │   Database   │
└─────────────┘                      └──────────────┘                     └──────────────┘
```

## 📊 Database Schema

### Collections and Relationships

#### 1. Users Collection
```javascript
{
  _id: ObjectId,
  name: String,
  email: String (unique, indexed),
  password: String (hashed),
  role: String (student/instructor/admin),
  profile: {                        // Embedded Document
    bio: String,
    avatar: String,
    phone: String,
    address: {
      street: String,
      city: String,
      country: String,
      zipCode: String
    }
  },
  stats: {                          // Embedded Document
    coursesCompleted: Number,
    totalLearningHours: Number,
    certificatesEarned: Number
  },
  isActive: Boolean,
  lastLogin: Date,
  createdAt: Date,
  updatedAt: Date
}
```

#### 2. Courses Collection
```javascript
{
  _id: ObjectId,
  title: String (indexed),
  description: String,
  instructor: ObjectId (ref: User),  // Referenced Document
  category: String (indexed),
  level: String,
  price: Number,
  thumbnail: String,
  metadata: {                        // Embedded Document
    duration: Number,
    language: String,
    totalLessons: Number,
    enrollmentCount: Number
  },
  requirements: [String],            // Embedded Array
  learningOutcomes: [String],        // Embedded Array
  ratings: {                         // Embedded Document
    average: Number,
    count: Number
  },
  status: String,
  publishedAt: Date,
  createdAt: Date,
  updatedAt: Date
}
```

#### 3. Lessons Collection
```javascript
{
  _id: ObjectId,
  title: String,
  description: String,
  course: ObjectId (ref: Course),    // Referenced Document
  order: Number (indexed),
  content: {                         // Embedded Document
    type: String (video/text/quiz/assignment),
    videoUrl: String,
    textContent: String,
    duration: Number,
    resources: [{
      title: String,
      url: String,
      type: String
    }]
  },
  isFree: Boolean,
  isPublished: Boolean,
  createdAt: Date,
  updatedAt: Date
}
```

#### 4. Enrollments Collection
```javascript
{
  _id: ObjectId,
  user: ObjectId (ref: User),        // Referenced Document
  course: ObjectId (ref: Course),    // Referenced Document
  progress: {                        // Embedded Document
    completedLessons: [{
      lesson: ObjectId (ref: Lesson),
      completedAt: Date
    }],
    percentage: Number,
    lastAccessedLesson: ObjectId (ref: Lesson),
    lastAccessedAt: Date
  },
  status: String (active/completed/dropped),
  rating: {                          // Embedded Document
    score: Number,
    review: String,
    ratedAt: Date
  },
  enrolledAt: Date,
  completedAt: Date,
  createdAt: Date,
  updatedAt: Date
}
```

### Data Modeling Strategy

**Embedded vs Referenced:**
- **Embedded**: Profile data, statistics, metadata (1-to-1 or 1-to-few)
- **Referenced**: Users, courses, lessons (1-to-many or many-to-many)

**Compound Indexes:**
1. `{ email: 1 }` - User lookup
2. `{ title: 1, category: 1 }` - Course search
3. `{ instructor: 1, status: 1 }` - Instructor dashboard
4. `{ user: 1, course: 1 }` - Enrollment uniqueness
5. `{ course: 1, status: 1 }` - Course analytics
6. `{ course: 1, order: 1 }` - Lesson ordering

## 🔌 API Documentation

### Base URL
```
http://localhost:5000/api
```

### Authentication Endpoints

#### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "student"
}

Response: {
  "success": true,
  "data": {
    "_id": "...",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "student",
    "token": "jwt_token_here"
  }
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}

Response: {
  "success": true,
  "data": {
    "_id": "...",
    "name": "John Doe",
    "token": "jwt_token_here"
  }
}
```

#### Get Current User
```http
GET /auth/me
Authorization: Bearer {token}

Response: {
  "success": true,
  "data": { user_object }
}
```

### Course Endpoints

#### Get All Courses (with filtering, pagination, sorting)
```http
GET /courses?page=1&limit=10&category=programming&search=javascript&sort=-createdAt

Response: {
  "success": true,
  "data": [ courses ],
  "pagination": {
    "total": 50,
    "page": 1,
    "pages": 5
  }
}
```

#### Get Single Course
```http
GET /courses/:id

Response: {
  "success": true,
  "data": { course_with_instructor_and_lessons }
}
```

#### Create Course (Instructor/Admin only)
```http
POST /courses
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Complete JavaScript Course",
  "description": "Learn JavaScript from scratch",
  "category": "programming",
  "level": "beginner",
  "price": 99.99,
  "requirements": ["Basic computer knowledge"],
  "learningOutcomes": ["Master JavaScript", "Build web apps"]
}
```

#### Update Course (Advanced operations)
```http
PUT /courses/:id
Authorization: Bearer {token}

{
  "title": "Updated Title",
  "price": 79.99,
  "addRequirement": "HTML knowledge",
  "removeRequirement": "Old requirement",
  "metadata": {
    "duration": 20,
    "language": "English"
  }
}

Uses: $set, $push, $pull, $inc operators
```

#### Delete Course
```http
DELETE /courses/:id
Authorization: Bearer {token}

Response: {
  "success": true,
  "data": {}
}
```

#### Get Course Statistics (Aggregation)
```http
GET /courses/stats/overview
Authorization: Bearer {token} (Admin only)

Response: {
  "success": true,
  "data": [
    {
      "_id": "programming",
      "totalCourses": 25,
      "averagePrice": 89.99,
      "totalEnrollments": 1250,
      "averageRating": 4.5
    }
  ]
}
```

#### Get Instructor Analytics (Advanced Aggregation)
```http
GET /courses/stats/instructor/:instructorId
Authorization: Bearer {token}

Response: {
  "success": true,
  "data": {
    "courses": [...],
    "totalRevenue": 15000,
    "totalEnrollments": 500,
    "totalCourses": 10
  }
}
```

### Enrollment Endpoints

#### Enroll in Course
```http
POST /enrollments
Authorization: Bearer {token}

{
  "courseId": "course_id_here"
}
```

#### Get My Enrollments
```http
GET /enrollments/my-courses
Authorization: Bearer {token}

Response: {
  "success": true,
  "data": [ enrollments_with_courses ]
}
```

#### Update Progress (Advanced update with $push)
```http
PUT /enrollments/:id/progress
Authorization: Bearer {token}

{
  "lessonId": "lesson_id_here"
}

Updates: completedLessons array, progress percentage, status
Uses: $push, $set, $inc
```

#### Add Rating
```http
PUT /enrollments/:id/rating
Authorization: Bearer {token}

{
  "score": 5,
  "review": "Great course!"
}
```

#### Unenroll
```http
DELETE /enrollments/:id
Authorization: Bearer {token}
```

### Lesson Endpoints

#### Get Course Lessons
```http
GET /lessons/course/:courseId

Response: {
  "success": true,
  "data": [ lessons_ordered_by_sequence ]
}
```

#### Create Lesson (Instructor/Admin only)
```http
POST /lessons
Authorization: Bearer {token}

{
  "title": "Introduction to Variables",
  "course": "course_id",
  "order": 1,
  "content": {
    "type": "video",
    "videoUrl": "https://...",
    "duration": 15
  }
}
```

#### Update Lesson
```http
PUT /lessons/:id
Authorization: Bearer {token}

{ updated_fields }
```

#### Delete Lesson
```http
DELETE /lessons/:id
Authorization: Bearer {token}
```

### User Endpoints

#### Get All Users (Admin only)
```http
GET /users
Authorization: Bearer {token}
```

#### Get User Profile
```http
GET /users/:id
Authorization: Bearer {token}
```

#### Update Profile (Advanced nested update)
```http
PUT /users/:id
Authorization: Bearer {token}

{
  "name": "Updated Name",
  "bio": "My bio",
  "address": {
    "city": "New York",
    "country": "USA"
  }
}

Uses: $set for nested fields
```

## 🔍 MongoDB Queries and Operations

### CRUD Operations

#### Create
```javascript
// Insert new course
await Course.create({
  title: "Course Title",
  instructor: userId,
  // ... other fields
});
```

#### Read
```javascript
// Find with population
await Course.find({ status: 'published' })
  .populate('instructor', 'name email')
  .sort('-createdAt')
  .limit(10);
```

#### Update - Advanced Operations
```javascript
// $set - Update specific fields
await Course.findByIdAndUpdate(id, {
  $set: { 
    title: "New Title",
    'metadata.duration': 20 
  }
});

// $push - Add to array
await Course.findByIdAndUpdate(id, {
  $push: { requirements: "New requirement" }
});

// $pull - Remove from array
await Course.findByIdAndUpdate(id, {
  $pull: { requirements: "Old requirement" }
});

// $inc - Increment/Decrement
await Course.findByIdAndUpdate(id, {
  $inc: { 'metadata.enrollmentCount': 1 }
});

// Positional operators
await Enrollment.findByIdAndUpdate(id, {
  $push: {
    'progress.completedLessons': {
      lesson: lessonId,
      completedAt: Date.now()
    }
  }
});
```

#### Delete
```javascript
// Delete with cascade
await Lesson.deleteMany({ course: courseId });
await Enrollment.deleteMany({ course: courseId });
await Course.findByIdAndDelete(courseId);
```

### Aggregation Framework

#### Multi-Stage Pipeline 1: Course Statistics by Category
```javascript
await Course.aggregate([
  // Stage 1: Filter published courses
  {
    $match: { status: 'published' }
  },
  // Stage 2: Group by category
  {
    $group: {
      _id: '$category',
      totalCourses: { $sum: 1 },
      averagePrice: { $avg: '$price' },
      totalEnrollments: { $sum: '$metadata.enrollmentCount' },
      averageRating: { $avg: '$ratings.average' }
    }
  },
  // Stage 3: Sort by total courses
  {
    $sort: { totalCourses: -1 }
  }
]);
```

#### Multi-Stage Pipeline 2: Instructor Analytics
```javascript
await Course.aggregate([
  // Stage 1: Match instructor courses
  {
    $match: {
      instructor: ObjectId(instructorId)
    }
  },
  // Stage 2: Join with enrollments
  {
    $lookup: {
      from: 'enrollments',
      localField: '_id',
      foreignField: 'course',
      as: 'enrollments'
    }
  },
  // Stage 3: Project calculated fields
  {
    $project: {
      title: 1,
      price: 1,
      totalEnrollments: { $size: '$enrollments' },
      activeEnrollments: {
        $size: {
          $filter: {
            input: '$enrollments',
            cond: { $eq: ['$$this.status', 'active'] }
          }
        }
      },
      revenue: {
        $multiply: ['$price', { $size: '$enrollments' }]
      }
    }
  },
  // Stage 4: Group for totals
  {
    $group: {
      _id: null,
      courses: { $push: '$$ROOT' },
      totalRevenue: { $sum: '$revenue' },
      totalEnrollments: { $sum: '$totalEnrollments' }
    }
  }
]);
```

#### Multi-Stage Pipeline 3: Update Course Ratings
```javascript
// Calculate average rating from enrollments
const ratings = await Enrollment.aggregate([
  {
    $match: {
      course: courseId,
      'rating.score': { $exists: true }
    }
  },
  {
    $group: {
      _id: null,
      averageRating: { $avg: '$rating.score' },
      count: { $sum: 1 }
    }
  }
]);
```

## 📈 Indexing and Optimization Strategy

### Compound Indexes
```javascript
// User email lookup (unique)
db.users.createIndex({ email: 1 }, { unique: true });

// Course search and filtering
db.courses.createIndex({ title: 1, category: 1 });

// Instructor dashboard queries
db.courses.createIndex({ instructor: 1, status: 1 });

// Enrollment uniqueness
db.enrollments.createIndex({ user: 1, course: 1 }, { unique: true });

// Course analytics
db.enrollments.createIndex({ course: 1, status: 1 });

// Lesson ordering
db.lessons.createIndex({ course: 1, order: 1 });
```

### Query Optimization Examples

#### Before Optimization
```javascript
// Slow - No index, full collection scan
await Course.find({ category: 'programming', level: 'beginner' });
```

#### After Optimization
```javascript
// Fast - Uses compound index { category: 1, level: 1 }
await Course.find({ category: 'programming', level: 'beginner' })
  .hint({ category: 1, level: 1 });
```

### Performance Justification

1. **Email Index**: Ensures fast user authentication lookups
2. **Category Index**: Speeds up course filtering and search
3. **Instructor Index**: Optimizes instructor dashboard queries
4. **Enrollment Compound Index**: Prevents duplicate enrollments and speeds up user course lookups
5. **Lesson Order Index**: Ensures fast sequential lesson retrieval

## 🔐 Authentication and Authorization

### JWT Implementation
- Token generated on login/register
- Token expires in 30 days
- Stored in localStorage on frontend
- Sent in Authorization header: `Bearer {token}`

### Role-Based Access Control
- **Student**: Can view courses, enroll, track progress
- **Instructor**: Can create/edit/delete own courses and lessons
- **Admin**: Full access to all resources

### Middleware Protection
```javascript
// Protect routes
router.post('/courses', protect, authorize('instructor', 'admin'), createCourse);
```

## 🚀 Setup Instructions

### Prerequisites
- Node.js (v16+)
- MongoDB (v5+)
- npm or yarn

### Backend Setup
```bash
cd backend
npm install
cp .env.example .env
# Edit .env with your MongoDB URI
npm run dev
```

### Frontend Setup
```bash
cd frontend
npm install
npm run dev
```

### Environment Variables
```env
PORT=5000
MONGODB_URI=mongodb://localhost:27017/online_course_platform
JWT_SECRET=your_secret_key
NODE_ENV=development
```

## 📝 Testing the Application

### Sample API Requests (Postman/Insomnia)

1. Register a user
2. Login to get token
3. Create a course (as instructor)
4. Create lessons for the course
5. Enroll in course (as student)
6. Update progress
7. Add rating

## 🎯 Key Features Implemented

### Database Requirements ✅
- ✅ CRUD operations across multiple collections
- ✅ Embedded documents (profile, metadata, ratings)
- ✅ Referenced documents (users, courses, lessons)
- ✅ Advanced update operators ($set, $push, $pull, $inc)
- ✅ Multi-stage aggregation pipelines
- ✅ Compound indexes
- ✅ Query optimization

### API Requirements (12+ endpoints) ✅
1. POST /auth/register
2. POST /auth/login
3. GET /auth/me
4. GET /courses (with pagination, filtering, sorting)
5. GET /courses/:id
6. POST /courses
7. PUT /courses/:id
8. DELETE /courses/:id
9. GET /courses/stats/overview (aggregation)
10. GET /courses/stats/instructor/:id (aggregation)
11. POST /enrollments
12. GET /enrollments/my-courses
13. PUT /enrollments/:id/progress
14. PUT /enrollments/:id/rating
15. DELETE /enrollments/:id
16. GET /lessons/course/:courseId
17. POST /lessons
18. PUT /lessons/:id
19. DELETE /lessons/:id

### Frontend Requirements (6 pages) ✅
1. Home Page - Browse courses
2. Course Detail Page - View course information
3. My Courses Page - Student dashboard
4. Instructor Dashboard - Manage courses
5. Create/Edit Course Page - Course management
6. Login/Register Page - Authentication

### Bonus Features ✅
- ✅ Proper environment configuration (.env)
- ✅ Centralized error handling
- ✅ Pagination, filtering, sorting
- ✅ JWT authentication & authorization
- ✅ Password hashing (bcrypt)
- ✅ Input validation
- ✅ Professional code structure

## 👥 Team Contribution

### Student 1: Backend & Database
- Database schema design
- MongoDB models with Mongoose
- RESTful API implementation
- Authentication & authorization
- Aggregation pipelines
- Indexing strategy

### Student 2: Frontend & Integration
- React component development
- UI/UX design
- API integration
- State management
- Routing implementation
- User authentication flow

## 📊 Project Statistics

- **Backend**: 5 models, 19+ API endpoints
- **Frontend**: 6 pages, responsive design
- **Database**: 4 collections, 6+ indexes
- **Features**: Authentication, CRUD, Aggregations, Progress tracking
- **Code Quality**: Modular, well-documented, error-handled

## 🎓 Learning Outcomes

This project demonstrates:
- Advanced NoSQL database design
- MongoDB aggregation framework mastery
- RESTful API best practices
- Modern full-stack development
- Security implementation
- Performance optimization
