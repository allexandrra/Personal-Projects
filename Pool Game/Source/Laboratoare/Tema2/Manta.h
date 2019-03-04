#pragma once

#include <Core/GPU/Mesh.h>

#include <Component/SimpleScene.h>
#include <string>
#include <Core/Engine.h>

using namespace std;

class Manta : public SimpleScene {
public:
	Manta();
	Manta(float xCoord, float yCoord, float zCoord, float width, float height, float lenght, Mesh *mesh, glm::vec3 color);
	~Manta();

public:
	//functii
	void updateCoord(float x, float y, float z);
	float getLenght();
	float getWidth();
	float getHeight();
	float getX();
	float getY();
	float getZ();
	Mesh* getMesh();
	void setMesh(Mesh* mesh);
	glm::vec3 getColor();

public:
	//variabile
	float xCoord, yCoord, zCoord, lenght, width, height;
	Mesh* mesh;
	glm::vec3 color;
};