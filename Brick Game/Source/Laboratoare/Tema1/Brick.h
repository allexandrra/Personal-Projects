#include <Core/GPU/Mesh.h>

#include <Component/SimpleScene.h>
#include <string>
#include <Core/Engine.h>

class Brick : public SimpleScene {
public:
	Brick(float length,float width);
	~Brick();
public:
	// functii
	Mesh* getBrick(glm::vec3 color, bool fill, std::string name);
	void updateCoordinates(float x, float y);
	float getXCoord();
	float getYCoord();
private:
	//variabile
	float length, width;
	float posX, posY;
	glm::vec3 corner;
};