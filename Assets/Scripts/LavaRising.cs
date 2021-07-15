using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LavaRising : MonoBehaviour
{
    [SerializeField] float scrollSpeed = 0.2f;

    // Update is called once per frame
    void Update()
    {
        float yMove = scrollSpeed * Time.deltaTime;
        // Move object upward 
        transform.Translate(new Vector2(0f, yMove));
    }
}
