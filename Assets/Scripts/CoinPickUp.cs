using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CoinPickUp : MonoBehaviour
{
    [SerializeField] AudioClip coinPickUpSound;
    private int pointsForCoin = 100;
    private bool coinPicked = false;

    private void OnTriggerEnter2D(Collider2D other)
    {
        // Check if coin was picked (To avoid double counting score)
        if (!coinPicked)
        {
            coinPicked = true;
            // Increment score
            FindObjectOfType<GameSession>().AddToScore(pointsForCoin);
            // Execute coin picking up sound effect
            AudioSource.PlayClipAtPoint(coinPickUpSound, Camera.main.transform.position);
            // When collided with player, destroy itself
            Destroy(gameObject);
        }
    }
}
