#pragma once

#include <Core/GPU/Mesh.h>

#include <Component/SimpleScene.h>
#include <string>
#include <Core/Engine.h>

using namespace std;

class Tac : public SimpleScene {
public:
	Tac();
	Tac(float xCoord, float yCoord, float zCoord, float width, float height, float lenght, Mesh* mesh, glm::vec3 color);
	~Tac();

public:
	//functii
	void updateCoord(float x, float y, float z);
	void updateX(float x);
	void updateY(float y);
	void updateZ(float z);
	void setX(float x);
	void setY(float y);
	void setZ(float z);
	float getX();
	float getY();
	float getZ();
	float getLenght();
	float getWidth();
	float getHeight();
	Mesh* getMesh();
	void setMesh(Mesh* mesh);
	glm::vec3 getColor();

public:
	//variabile
	float xCoord, yCoord, zCoord, lenght, width, height;
	Mesh* mesh;
	glm::vec3 color;
};