#include <Core/GPU/Mesh.h>

#include <Component/SimpleScene.h>
#include <string>
#include <Core/Engine.h>

class Ball : public SimpleScene {
public:
	Ball(float radius);
	~Ball();
public:
	// functii
	Mesh* getBall(glm::vec3 color, bool fill, std::string name);
	void updateCoordinates(float x, float y);
	float getXCoord();
	float getYCoord();
private:
	//variabile
	float radius, centerX, centerY;
	glm::vec3 corner;
};