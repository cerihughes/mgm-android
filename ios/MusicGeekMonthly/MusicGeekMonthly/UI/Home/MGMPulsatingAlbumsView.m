//
//  MGMPulsatingAlbumsView.m
//  Music Geek Monthly
//
//  Created by Ceri Hughes on 23/07/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMPulsatingAlbumsView.h"
#import "MGMAlbumView.h"

@interface MGMPulsatingAlbumsView ()

@property (strong) NSMutableArray* albumViews;

@end

@implementation MGMPulsatingAlbumsView

- (NSUInteger) albumCount
{
    return self.albumViews.count;
}

- (void) setupAlbumsInRow:(NSUInteger)albumsInRow
{
    self.albumViews = [NSMutableArray array];
    
    CGSize size = self.frame.size;
    CGFloat width = size.width;
    CGFloat height = size.height;
    CGFloat albumWidth = width / albumsInRow;
    NSUInteger albumsInColumn = (height / albumWidth) + 1;

    for (NSUInteger row = 0; row < albumsInRow; row++)
    {
        for (NSUInteger column = 0; column < albumsInColumn; column++)
        {
            CGRect frame = CGRectMake(row * albumWidth, column * albumWidth, albumWidth, albumWidth);
            MGMAlbumView* imageView = [[MGMAlbumView alloc] initWithFrame:frame];
            imageView.alphaOn = 0.15;
            imageView.animationTime = 3;
            [self addSubview:imageView];
            [self.albumViews addObject:imageView];
        }
    }
}

- (CGSize) albumSize
{
    if (self.albumViews.count > 0)
    {
        MGMAlbumView* view = [self.albumViews objectAtIndex:0];
        return view.frame.size;
    }
    return CGSizeZero;
}

- (void) renderImage:(UIImage*)image atIndex:(NSUInteger)index animation:(BOOL)animation
{
    if (index < self.albumCount)
    {
        if (animation)
        {
            [[self.albumViews objectAtIndex:index] fadeOutAndRenderImage:image];
        }
        else
        {
            [[self.albumViews objectAtIndex:index] renderImageWithNoAnimation:image];
        }
    }
}

@end