#!/bin/bash

BASE="src/main"
JAVA="$BASE/java"
RES="$BASE/resources"

echo "📁 Creating unified project structure..."

# Java folders
mkdir -p $JAVA/controller
mkdir -p $JAVA/service
mkdir -p $JAVA/model/entity
mkdir -p $JAVA/util

# Resource folders
mkdir -p $RES/view/auth
mkdir -p $RES/view/dashboard
mkdir -p $RES/view/students
mkdir -p $RES/view/courses

echo "📦 Moving Controllers..."
mv testing/StudentsController.java $JAVA/controller/ 2>/dev/null
mv testing/CoursesController.java  $JAVA/controller/ 2>/dev/null

echo "📦 Moving Services..."
mv testing/StudentService.java $JAVA/service/ 2>/dev/null
mv testing/CourseService.java  $JAVA/service/ 2>/dev/null

echo "📦 Moving Validator..."
mv testing/Validator.java $JAVA/util/ 2>/dev/null

echo "📦 Moving Entities..."
mv testing/entity/* $JAVA/model/entity/ 2>/dev/null
rmdir testing/entity 2>/dev/null

echo "📦 Moving Student Views..."
mv testing/StudentListView.fxml $RES/view/students/ 2>/dev/null
mv testing/AddMemberView.fxml   $RES/view/students/ 2>/dev/null

echo "📦 Moving Course Views..."
mv testing/CourseListView.fxml $RES/view/courses/ 2>/dev/null
mv testing/CourseView.fxml     $RES/view/courses/ 2>/dev/null

echo "📦 Organizing Part 2 Views..."
mv src/main/resources/view/login.fxml     $RES/view/auth/ 2>/dev/null
mv src/main/resources/view/register.fxml  $RES/view/auth/ 2>/dev/null
mv src/main/resources/view/dashboard.fxml $RES/view/dashboard/ 2>/dev/null

echo "✅ Merge & organization completed successfully!"
