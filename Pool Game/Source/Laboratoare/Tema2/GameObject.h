#include <Core/GPU/Mesh.h>

#include <Component/SimpleScene.h>
#include <string>
#include <Core/Engine.h>

class GameObject : public SimpleScene {
public:
	GameObject(float xCoord, float yCoord, float zCoord, string name, glm::vec3 color);
	~GameObject();

public:
	//functii
	void updateCoord(float x, float y, float z);
	void createObject();

public:
	//variabile
	float xCoord, yCoord, zCoord;
	string name;
	glm::vec3 color;
};