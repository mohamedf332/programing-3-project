#!/bin/bash

BASE="src/main"
JAVA="$BASE/java"
RES="$BASE/resources"

echo "📁 Creating directory structure..."

mkdir -p $JAVA/controller
mkdir -p $JAVA/service
mkdir -p $JAVA/model/entity
mkdir -p $JAVA/util

mkdir -p $RES/view/students
mkdir -p $RES/view/courses

echo "📦 Moving Controllers..."
mv CoursesController.java $JAVA/controller/ 2>/dev/null
mv StudentsController.java $JAVA/controller/ 2>/dev/null

echo "📦 Moving Services..."
mv StudentService.java $JAVA/service/ 2>/dev/null
mv CourseService.java  $JAVA/service/ 2>/dev/null

echo "📦 Moving Validator..."
mv Validator.java $JAVA/util/ 2>/dev/null

echo "📦 Moving Entities..."
mv entity/* $JAVA/model/entity/ 2>/dev/null
rmdir entity 2>/dev/null

echo "📦 Moving Student Views..."
mv StudentListView.fxml $RES/view/students/ 2>/dev/null
mv AddMemberView.fxml   $RES/view/students/ 2>/dev/null

echo "📦 Moving Course Views..."
mv CourseListView.fxml  $RES/view/courses/ 2>/dev/null
mv CourseView.fxml      $RES/view/courses/ 2>/dev/null

echo "✅ Organization completed successfully!"
