
#import "MGMUI.h"

#import "UIViewController+MGMAdditions.h"

#import "MGMTransitionViewController.h"
#import "MGMWeeklyChartViewController.h"
#import "MGMPlaylistsViewController.h"

@interface MGMUI ()

@property (retain) NSMutableDictionary* transitions;

- (void) setupCore;
- (void) setupControllers;

@end

@implementation MGMUI

- (id) init
{
    if (self = [super init])
    {
        [self setupCore];
        [self setupControllers];
        self.imageCache = [[MGMImageCache alloc] init];
    }
    return self;
}

- (void) setupCore
{
    self.core = [[MGMCore alloc] init];
}

- (void) setupControllers
{
    self.transitions = [NSMutableDictionary dictionary];
    
    MGMWeeklyChartViewController* weeklyChartViewController = [[MGMWeeklyChartViewController alloc] init];
    weeklyChartViewController.ui = self;
    [self.transitions setObject:weeklyChartViewController forKey:TO_CHART];

    MGMPlaylistsViewController* playlistsViewController = [[MGMPlaylistsViewController alloc] init];
    playlistsViewController.ui = self;
    [self.transitions setObject:playlistsViewController forKey:TO_PLAYLISTS];

    self.parentViewController = [[MGMTransitionViewController alloc] init];
    self.parentViewController.initialViewController = playlistsViewController;
}

- (void) transition:(NSString *)transition
{
    [self transition:transition withState:nil];
}

- (void) transition:(NSString *)transition withState:(id)state
{
    UIViewController* controller = [self.transitions objectForKey:transition];
    if (![self.parentViewController.currentViewController isEqual:controller])
    {
        [self.parentViewController transitionTo:controller];
        [controller transitionCompleteWithState:state];
    }
}

- (void) enteringBackground
{
    [self.core enteringBackground];

	if ([[UIApplication sharedApplication] setKeepAliveTimeout:600 handler:^
    {
		NSLog(@"Keepalive handler running.");
		[self.core keepAlive];
	}])
    {
		NSLog(@"Registered keepalive handler.");
	}
}

- (void) enteredForeground
{
    [self.core enteredForeground];
}

- (void) timeChanged
{
}

@end
