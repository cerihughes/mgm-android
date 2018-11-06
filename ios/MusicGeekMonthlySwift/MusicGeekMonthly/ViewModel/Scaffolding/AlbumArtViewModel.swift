import UIKit

protocol AlbumArtViewModel {
    /// An image to show while the album art is loading
    var loadingImage: UIImage? {get}

    /// Loads an album cover. Image data will be passed into the given completion block
    ///
    /// - Parameter largestDimension: the largest dimension of the image to load (the loaded
    ///                               image can be larger or smaller - this is just a guide)
    /// - Parameter completion: the completion block
    func loadAlbumCover(largestDimension: Int, _ completion: @escaping (UIImage?) -> Void)

    /// Cancels any ongoing image load.
    func cancelLoadAlbumCover()
}

class AlbumArtViewModelImplementation: AlbumArtViewModel {
    private let imageLoader: ImageLoader
    private let images: Images
    private let imageURLFormat: String
    private let loadingImageIndex: Int

    private var dataLoaderToken: DataLoaderToken? = nil

    init(imageLoader: ImageLoader, images: Images, imageURLFormat: String, loadingImageIndex: Int = -1) {
        self.imageLoader = imageLoader
        self.images = images
        self.imageURLFormat = imageURLFormat
        let range = 1 ... 3
        self.loadingImageIndex = range ~= loadingImageIndex ? loadingImageIndex : Int.random(in: range)
    }

    var loadingImage: UIImage? {
        let imageName = "album\(loadingImageIndex)"
        return UIImage(named: imageName)
    }

    func loadAlbumCover(largestDimension: Int, _ completion: @escaping (UIImage?) -> Void) {
        guard let imageID = images.image(for: largestDimension) else {
            return
        }

        var url = URL(string: imageID)
        if url?.scheme == nil {
            let urlString = String(format: imageURLFormat, imageID)
            url = URL(string: urlString)
        }

        guard let imageURL = url else {
            completion(nil)
            return
        }

        dataLoaderToken = imageLoader.loadImage(url: imageURL) { (response) in
            DispatchQueue.main.async {
                switch (response) {
                case .success(let image):
                    completion(image)
                case .failure(_):
                    completion(nil)
                }
            }
        }
    }

    func cancelLoadAlbumCover() {
        dataLoaderToken?.cancel()
        dataLoaderToken = nil
    }
}